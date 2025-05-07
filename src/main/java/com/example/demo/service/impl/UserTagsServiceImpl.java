package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserTags;
import com.example.demo.mapper.UserTagsMapper;
import com.example.demo.service.UserTagsService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserTagsServiceImpl implements UserTagsService {
	
	private final UserTagsMapper userTagsMapper;
	@Override
	public void saveUserTags(Integer user_id, List<Integer> tags_id) {
		//既存のタグを削除
		userTagsMapper.deleteByUserId(user_id);
		
		//6つまでに制限
		List<UserTags> userTags = tags_id.stream()
				.limit(6)
				.map(tagid ->{
					UserTags ut = new UserTags();
					ut.setUser_id(user_id);
					ut.setTag_id(tagid);
					return ut;
				})
				.collect(Collectors.toList());
		
		 if (!userTags.isEmpty()) 
		userTagsMapper.insertUserTags(userTags);
		
	}

}
