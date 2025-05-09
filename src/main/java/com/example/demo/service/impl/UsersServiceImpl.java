package com.example.demo.service.impl;

import java.io.IOException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
		/// 既存のユーザー情報を取得
	    User existingUser = usersMapper.selectByIdUsers(users.getUser_id());

	    // パスワードの更新判定（入力時ハッシュ化して更新）
	    if (StringUtils.hasText(users.getNewPassword())) { 
	        String hashedPass = passwordEncoder.encode(users.getNewPassword());
	        users.setUser_pass(hashedPass);
	    } else {// 未入力なら既存のパスワードを保持
	    	users.setUser_pass(existingUser.getUser_pass());
	    }
	    
	    // アイコンの更新判定（登録があれば画像を保持）
	    if (users.getIconFile() != null && !users.getIconFile().isEmpty()) {
	    	try {
	    		users.setUser_icon(users.getIconFile().getBytes());
	        } catch (IOException e) {
	            throw new RuntimeException("画像の読み込みに失敗しました", e);
	        }
	    } else {// 未登録なら元の画像を保持
	        users.setUser_icon(existingUser.getUser_icon()); 
	    }

	    // ユーザー情報を更新
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
