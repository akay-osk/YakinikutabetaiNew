package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.User;

/*
 * ユーザー用サービス
 * 作成者　奥野
 */


public interface UsersService {

	//	ユーザー情報新規登録
	void insertUsers(User users);
	
	//	ユーザー情報更新
	void updateUsers(User users);
	
	//  ユーザー情報削除
	void deleteUsers(Integer id);
	
	// ユーザー情報取得
	User findByIdUsers(Integer id);

	//CustomUserDetails用
	Optional<User> findByUsername(String username);
}
