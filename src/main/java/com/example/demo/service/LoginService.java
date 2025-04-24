package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	 public boolean login(String user_name, String user_pass) {
	        LoginEntity user = login_mapper.findByLogin(user_name, user_pass);
	        return user != null;
	    }
	
}
