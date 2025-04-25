package com.example.demo.entity;

import lombok.Data;

/*
 * ユーザー用エンティティ
 * 作成者　奥野
 */
 
@Data
public class User 
{
	private int user_id;
	private String user_pass;
	private String user_name;
	private int user_age;
	private boolean user_gender;
	private String user_likes;
	private String user_detail;
	private byte[] user_icon;
	private String user_addrres;
}
