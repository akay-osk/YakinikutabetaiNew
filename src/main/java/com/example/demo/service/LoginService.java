package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.LoginEntity;
import com.example.demo.mapper.Login_mapper;

/*
 * DBに同じ情報があるか確認してるやつ 
 * 作成者 北川
 */

@Service
public class LoginService {

	@Autowired
	private Login_mapper login_mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//input_passはユーザーが画面で入力したパスワード
	 public boolean login(String user_name, String input_pass) {
		 
		 	//DBからユーザ名を取得
	        LoginEntity user = login_mapper.findByName(user_name);
	        
	        //DBにユーザ名が見つからんかった場合
	        if(user == null) return false;
	        
	        //入力されたpassとDBに保存されたハッシュ化されたpassを比較。
	        //user.getUser_pass()はハッシュ化されたpass
	        return passwordEncoder.matches(input_pass, user.getUser_pass());	    
	       
	 }
}