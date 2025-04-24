package com.example.demo.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.example.demo.service.LoginService;

/*
 * DBと照合してログインできるか判定してるやつ
 * 作成者 北川
 */

@Component
public class LoginCustom implements AuthenticationProvider{

	@Autowired
	private LoginService loginService;
	
	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String user_name = authentication.getName();
        String user_pass = authentication.getCredentials().toString();

        if (loginService.login(user_name, user_pass)) {
            return new UsernamePasswordAuthenticationToken(user_name, user_pass, Collections.emptyList());
        } else {
            throw new BadCredentialsException("ユーザー名またはパスワードが間違っています");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

	
}
