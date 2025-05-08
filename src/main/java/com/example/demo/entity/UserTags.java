package com.example.demo.entity;

import lombok.Data;
/*
 * ユーザータグ登録用中間マッパー
 * 作成者　奥野
 */
@Data
public class UserTags {
	private Integer user_id;
	private Integer tag_id;
	
 public UserTags(Integer user_id, Integer tag_id) {
	this.user_id = user_id;
	this.tag_id = tag_id;
	
 	}
 
}
