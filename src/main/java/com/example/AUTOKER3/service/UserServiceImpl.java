package com.example.AUTOKER3.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.AUTOKER3.beans.Role;
import com.example.AUTOKER3.beans.User;
import com.example.AUTOKER3.beans.UserRegistrationDto;
import com.example.AUTOKER3.configuration.SpringSecurityConfiguration;
import com.example.AUTOKER3.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	private UserRepository userRepository;
	
	public static Collection<Role> roles ;
	
    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) throws UsernameNotFoundException{
//    	if(registrationDto.getFirstName().equalsIgnoreCase("ADMIN") && registrationDto.getLastName().equalsIgnoreCase("ADMIN")) {
//    		User user = new User(registrationDto.getFirstName(),
//    	            registrationDto.getLastName(), registrationDto.getEmail(),
//    	            SpringSecurityConfiguration.passwordEncoder().encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_ADMIN")));
//    		 return userRepository.save(user);
//    	}else {
    	
    		User user = new User(registrationDto.getFirstName(),
    	            registrationDto.getLastName(), registrationDto.getEmail(),
    	            SpringSecurityConfiguration.passwordEncoder().encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));
    		 return userRepository.save(user);
//    	}
       
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        roles = user.getRoles();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(roles));
    }

    private Collection < ? extends GrantedAuthority > mapRolesToAuthorities(Collection < Role > roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
