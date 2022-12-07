package com.example.AUTOKER3.security;

/**
 * import static org.springframework.security.config.Customizer.withDefaults;
 */

import static org.springframework.security.config.Customizer.withDefaults;


import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

//	@Bean
//	public InMemoryUserDetailsManager createAdminDetailsManager() {
//		UserDetails userDetails1 = createNewUser("admin", "password");
//		return new InMemoryUserDetailsManager(userDetails1);
//	}
//	
//	private UserDetails createNewUser(String username, String password) {
//		Function<String, String> passwordEncoder
//		= input -> passwordEncoder().encode(input);
//
//		UserDetails userDetails = User.builder()
//									.passwordEncoder(passwordEncoder)
//									.username(username)
//									.password(password)
//									.roles("USER","ADMIN")
//									.build();
//		return userDetails;
//	}
//	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build();

        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder.encode("admin"))
            .roles("USER", "ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated());
        
        http.formLogin().defaultSuccessUrl("/",true);		// ez miatt nem dobott at a nyito oldalra bejelentkezes utan!!!
        http.csrf().disable();
        http.headers().frameOptions().disable();
        return http.build();
    }

}
