package com.example.demo.entity;

import java.util.List;

import lombok.Data;

/*
 * アイコンポップアップ用ユーザーデータ
 * 作成者　奥野
 * 
 */

@Data
public class UserProfile {
	private String userName;
	private int userAge;
	private String gender; // 表示用にtrue/false → "女性"/"男性"
	private String likes;
	private String detail;
	private List<String> tagNames;
	private String userIcon;

	public UserProfile(String userName, int userAge, boolean gender, String likes, String detail, List<String> tagNames, String userIcon) {
		this.userName = userName;
		this.userAge = userAge;
		this.gender = gender ? "女性" : "男性";
		this.likes = likes;
		this.detail = detail;
		this.tagNames = tagNames;
		this.userIcon = userIcon;
	}
	
	
}
