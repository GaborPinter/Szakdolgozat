package com.example.AUTOKER3.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.AUTOKER3.service.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	

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
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.withUsername("user")
//            .password(passwordEncoder.encode("password"))
//            .roles("USER")
//            .build();
//
//        UserDetails admin = User.withUsername("admin")
//            .password(passwordEncoder.encode("admin"))
//            .roles("USER", "ADMIN")
//            .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////    	http.authorizeHttpRequests(
////				auth -> auth.anyRequest().authenticated());
//        
//        http.formLogin().defaultSuccessUrl("/",true);		// true -- ez miatt nem dobott at a nyito oldalra bejelentkezes utan!!!
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
//        return http.build();
//    }

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	public void configure(HttpSecurity http) throws Exception {
		http.formLogin().defaultSuccessUrl("/home", true);
		http.csrf().disable();		//ez kell hogy megfeleloen mukodjon a paypal
		http.headers().frameOptions().disable();
		
		http.authorizeRequests().antMatchers("/registration**", "/js/**", "/css/**", "/img/**").permitAll().anyRequest()
				.authenticated().and().formLogin().loginPage("/loginPage").permitAll().and().logout()
				.invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).
				logoutSuccessUrl("/loginPage?logout")
				.permitAll();
		
		
		
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

}
