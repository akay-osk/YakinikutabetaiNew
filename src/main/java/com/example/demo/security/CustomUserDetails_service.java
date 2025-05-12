package com.example.demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.service.UsersService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetails_service implements UserDetailsService {
	private final UsersService usersService;

	 @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        return usersService.findByUsername(username)
	                .map(CustomUserDetails::new) // CustomUserDetailsはUserDetailsを実装したクラス
	                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	    }

}
