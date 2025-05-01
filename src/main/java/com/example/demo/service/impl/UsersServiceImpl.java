package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;
import com.example.demo.mapper.UsersMapper;
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
	
	@Autowired //ハッシュ化追加 キタガワ
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void insertUsers(User users) {
		//ハッシュ化追加 キタガワ
		String hashedPass = passwordEncoder.encode(users.getUser_pass());
		users.setUser_pass(hashedPass);
	
		usersMapper.insert(users);
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
