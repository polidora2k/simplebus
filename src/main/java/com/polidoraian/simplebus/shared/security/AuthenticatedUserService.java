package com.polidoraian.simplebus.shared.security;

import java.util.Collection;



import com.polidoraian.simplebus.shared.repository.UserRepository;
import com.polidoraian.simplebus.shared.entity.User;
import com.polidoraian.simplebus.shared.dto.UserCreationDTO;
import com.polidoraian.simplebus.shared.dto.UserDTO;
import com.polidoraian.simplebus.shared.dto.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class AuthenticatedUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;

	// added @Lazy to this to prevent a circular loading reference in component scan
	// https://stackoverflow.com/questions/65807838/spring-authenticationmanager-and-circular-dependency
	//@Lazy
	@Autowired
	private AuthenticationManager authenticationManager;

	public String getCurrentUsername() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null && context.getAuthentication() != null) {
			final org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) context.getAuthentication().getPrincipal();
			return principal.getUsername();
		} else {
			return null;
		}
	}

	public boolean isUserInRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null && context.getAuthentication() != null) {
			Collection<? extends GrantedAuthority> authorities = context.getAuthentication().getAuthorities();
			for (GrantedAuthority authority : authorities) {
				if (authority.getAuthority().equals(role)) {
					return true;
				}
			}
		}

		return false;
	}

	public UserDTO getCurrentUser() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		User user = (User) session.getAttribute("user");
		if (user == null) {
			user = userRepository.findByEmail(getCurrentUsername());
			session.setAttribute("user", user);
		}
		return userMapper.entityToDto(user);
	}
	
	public boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return false;
		}

		return (authentication != null && authentication.isAuthenticated());
	}
	
	//Modified function from post to perform auto login after sign up
	//https://stackoverflow.com/questions/3813028/auto-login-after-successful-registration
	public void loginNewUser(UserCreationDTO user, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		
		request.getSession(true);
		
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}
}
