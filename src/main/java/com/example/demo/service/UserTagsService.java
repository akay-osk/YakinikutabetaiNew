package com.example.demo.service;

import java.util.List;

/*
 * ユーザーとタグの中間テーブル用サービス
 * 作成者　奥野
 */
public interface UserTagsService {
	
	// ユーザーIDに対して最大６つのタグIDを保存
	void saveUserTags(Integer userId, List<Integer> tagsId);
	

}
