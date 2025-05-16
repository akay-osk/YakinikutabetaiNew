package com.example.demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.service.UsersService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetails_service implements UserDetailsService {
	private final UsersService usersService;

//	 @Override
//	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	        return usersService.findByUsername(username)
//	                .map(CustomUserDetails::new) // CustomUserDetailsはUserDetailsを実装したクラス
//	                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//	    }
	
	//UserDetailsServiceがCustomUserDetailsを返していないので修正(5/16オクダ)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user= usersService.findByUsername(username).
				orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new CustomUserDetails(user); //OK!
	}

}
