package com.example.demo.service.impl;

import java.io.IOException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.service.UserTagsService;
import com.example.demo.service.UsersService;
/*
 * ユーザーサービス実装
 * 作成者　奥野
 * 
 */

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor

public class UsersServiceImpl implements UsersService {
	
	private final UsersMapper usersMapper;
	private final UserTagsService userTagsService; //DI上手くいかんから定義

	
		//ハッシュ化追加 キタガワ
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void insertUsers(User users) {
		//ハッシュ化追加 キタガワ
		String hashedPass = passwordEncoder.encode(users.getUser_pass());
		users.setUser_pass(hashedPass);
		
		//
		if (users.getIconFile() != null && !users.getIconFile().isEmpty()) {
	        try {
	            users.setUser_icon(users.getIconFile().getBytes());
	        } catch (IOException e) {
	            throw new RuntimeException("画像の読み込みに失敗しました", e);
	        }
		}
		
		//ユーザー情報を保存
		usersMapper.insert(users);
		
		//タグ情報を保存
		if (users.getTag_id() != null && !users.getTag_id().isEmpty()) {
		    userTagsService.saveUserTags(users.getUser_id(), users.getTag_id());
		}
	}
	
	@Override
	public void updateUsers(User users) {
		//アップデート時もハッシュ化 キタガワ
		if(users.getUser_pass() != null && !users.getUser_pass().isEmpty()) {
	    String hashedPass = passwordEncoder.encode(users.getUser_pass());
	    users.setUser_pass(hashedPass);
	   }
		usersMapper.update(users);
	}

	@Override
	public void deleteUsers(Integer id) {
		usersMapper.delete(id);
	}

	@Override
	public User findByIdUsers(Integer id) {
		return usersMapper.selectByIdUsers(id);
	}
	

	 
}
