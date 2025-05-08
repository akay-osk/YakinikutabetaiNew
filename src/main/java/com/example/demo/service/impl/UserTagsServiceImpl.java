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
	public void saveUserTags(Integer userId, List<Integer> tagsId) {
		//既存のタグを削除
		userTagsMapper.deleteByUserId(userId);
		
		//6つまでに制限
		List<UserTags> userTags = tagsId.stream()
			    .limit(6)
			    .map(tagId -> new UserTags(userId, tagId))
			    .collect(Collectors.toList());
		
		 if (!userTags.isEmpty()) 
			 userTagsMapper.insertUserTags(userTags);
		
	}

}
