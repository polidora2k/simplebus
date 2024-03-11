package com.polidoraian.simplebus.shared.config.profiles;

import com.polidoraian.simplebus.web.WebApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("web")
@ComponentScan(basePackageClasses = WebApplication.class)
public class WebProfile {
}
