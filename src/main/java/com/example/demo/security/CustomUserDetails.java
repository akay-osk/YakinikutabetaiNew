package com.example.demo.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails{
	private final int userId;
	private final int roomId;
	private final String username;
	private final String password;
	
	//今回は権限ないのでnull
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
}
