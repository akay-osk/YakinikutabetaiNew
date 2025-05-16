package com.example.demo.service.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.demo.entity.User;
import com.example.demo.mapper.MatchingMapper;
import com.example.demo.mapper.Room_mapper;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.UserTagsService;
import com.example.demo.service.UsersService;
/*
 * ユーザーサービス実装
 * 作成者　奥野
 * 
 */

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
@Data
public class UsersServiceImpl implements UsersService {
	
	private final UsersMapper usersMapper;
	private final UserTagsService userTagsService; //DI上手くいかんから定義

	private final Room_mapper room_mapper;
	private final MatchingMapper matchingMapper;
	
		//ハッシュ化追加 キタガワ
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void insertUsers(User users) {
		//ハッシュ化追加 キタガワ
		String hashedPass = passwordEncoder.encode(users.getUser_pass());
		users.setUser_pass(hashedPass);
		
		
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
	    if (users.getUser_icon() == null && users.getUser_icon().isEmpty()) {
	    	// 未登録なら元の画像を保持
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

	@Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(usersMapper.findByUsername(username));
    }
	@Override
	public int getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		int currentUserId = userDetails.getUserId();
        return currentUserId;
	}

	@Override
	public boolean hasRoom(int userId) {
		return room_mapper.findRoomByUserId(userId) != null;
	}

	@Override
	public boolean hasWaitingCondition(int userId) {
		return matchingMapper.countMatchingByUserId(userId) > 0;
	}

	 
}
