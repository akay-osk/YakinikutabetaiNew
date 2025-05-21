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
		public void saveUserTags(Integer userId, List<Integer> tagIds) {
	        if (tagIds == null || tagIds.isEmpty()) {
	            return; // 空のリストなら何もしない
	        }

	        // タグの上限を6個に制限
	        List<Integer> limitedTagIds = tagIds.stream().limit(6).collect(Collectors.toList());

	        // ユーザーの既存タグを削除
	        userTagsMapper.deleteAllTagsByUserId(userId);

	        // タグ登録処理
	        List<UserTags> userTags = limitedTagIds.stream()
	            .map(tagId -> new UserTags(userId, tagId))
	            .collect(Collectors.toList());

	        userTagsMapper.insertUserTags(userTags);
	    }
	
	
	
		@Override
		public List<String> getTagNamesForUser(int userId) {
	    return  userTagsMapper.findTagNamesByUserId(userId);
	}
		
		//指定user_idのタグ情報削除
		@Override
		public void deleteAllTags(Integer userId) {
	        userTagsMapper.deleteAllTagsByUserId(userId);
		}

		
		

}
