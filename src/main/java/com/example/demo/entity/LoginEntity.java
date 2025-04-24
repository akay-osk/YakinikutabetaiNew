package com.example.demo.entity;

import lombok.Data;

//DBユーザの情報を表すクラス

@Data
public class LoginEntity {
	
	private String user_name;
	private String user_pass;
	
}
