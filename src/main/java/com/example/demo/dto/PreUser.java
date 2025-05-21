package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class PreUser {

	private String user_name;
	private String user_pass;
	private int user_age;
	private String user_gender;
	private String user_likes;
	private String user_detail;
	private String user_icon;
	List<Integer> tags;
}
