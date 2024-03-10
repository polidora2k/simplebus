package com.polidoraian.simplebus.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.polidoraian.simplebus.database.dao.UserDAO;
import com.polidoraian.simplebus.database.dao.UserRoleDAO;
import com.polidoraian.simplebus.database.entity.User;
import com.polidoraian.simplebus.database.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("Username '" + username + "' not found in database");
		}

		List<UserRole> userRoles = userRoleDAO.findByUserId(user.getId());

		// check the account status
		boolean accountIsEnabled = true;

		// spring security configs
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		// setup user roles
		Collection<? extends GrantedAuthority> springRoles = buildGrantAuthorities(userRoles);

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), accountIsEnabled, accountNonExpired, credentialsNonExpired, accountNonLocked, springRoles);
	}
	
	private Collection<? extends GrantedAuthority> buildGrantAuthorities(List<UserRole> userRoles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (UserRole role : userRoles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}

		return authorities;
	}
}
