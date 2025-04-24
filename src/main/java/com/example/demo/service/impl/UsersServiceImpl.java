package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Users;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.service.UsersService;
/*
 * ユーザーサービス実装
 * 作成者　奥野
 */

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor

public class UsersServiceImpl implements UsersService {
	
	private final UsersMapper usersMapper;
	
	@Override
	public void insertUsers(Users users) {
		usersMapper.insert(users);
	}

	
	@Override
	public void updateUsers(Users users) {
		usersMapper.update(users);
	}

	@Override
	public void deleteUsers(Integer id) {
		usersMapper.delete(id);
	}

	@Override
	public Users findByIdUsers(Integer id) {
		return usersMapper.selectByIdUsers(id);
	}

}
