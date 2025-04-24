package com.example.demo.service;

import com.example.demo.entity.Users;

/*
 * ユーザー用サービス
 * 作成者　奥野
 */

public interface UsersService {

	//	ユーザー情報新規登録
	void insertUsers(Users users);
	
	//	ユーザー情報更新
	void updateUsers(Users users);
	
	//  ユーザー情報削除
	void deleteUsers(Integer id);
	
	// ユーザー情報取得
	Users findByIdUsers(Integer id);
	

}
