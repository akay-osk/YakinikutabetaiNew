package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * パスワードエンコーダー
 * 作成 北川
 * 
 * **/

@Configuration
public class PasswordConfig {

	@Bean
	 PasswordEncoder passwordEncoder() {
		
		//エンコードの設定
		return new BCryptPasswordEncoder();	
		
	}
}
