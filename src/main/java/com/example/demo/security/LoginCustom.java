package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.service.LoginService;
import com.example.demo.service.UsersService;
 
/*
 * DBと照合してログインできるか判定してるやつ 
 * 作成者 北川
 * LoginCustomの認証後にCustomUserDetailsをAuthenticationに格納するように修正(5/16オクダ)
 */

@Component
public class LoginCustom implements AuthenticationProvider{

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UsersService usersService;
	
	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String user_name = authentication.getName();
        String user_pass = authentication.getCredentials().toString();
        
        //ログイン検証
        if (loginService.login(user_name, user_pass)) {
        	
        	//CustomUserDetails を取得
        	CustomUserDetails userDetails = usersService.findByUsername(user_name)
        			.map(CustomUserDetails::new)
        			.orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません: " + user_name));
        	
        	//principal に CustomUserDetails を設定
            return new UsernamePasswordAuthenticationToken(
            		userDetails, 
            		null, 
            		userDetails.getAuthorities()
            );
        } else {
            throw new BadCredentialsException("ユーザー名またはパスワードが間違っています");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

}
