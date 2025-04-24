package com.example.demo.entity;

import lombok.Data;

/*
 * DBユーザの情報を表すクラス 
 * 作成者 北川
 */
@Data
public class LoginEntity {
	
	private String user_name;
	private String user_pass;
	
}
