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
    public void saveUserTags(Integer userId, List<Integer> newTags) {
        // 既存のタグ一覧を取得
        List<Integer> existingTags = userTagsMapper.selectTagsByUserId(userId);

        // 新規登録時の処理（既存のタグがない場合はそのまま登録）
        if (existingTags.isEmpty()) {
            List<UserTags> userTags = newTags.stream()
                .limit(6) // 6つまでに制限
                .map(tagId -> new UserTags(userId, tagId))
                .collect(Collectors.toList());
            if (!userTags.isEmpty()) {
                userTagsMapper.insertUserTags(userTags);
            }
            return; // 差分更新の処理は不要
        }

        // 差分更新のための比較処理
        List<Integer> tagsToAdd = newTags.stream()
            .filter(tag -> !existingTags.contains(tag))
            .collect(Collectors.toList());

        List<Integer> tagsToRemove = existingTags.stream()
            .filter(tag -> !newTags.contains(tag))
            .collect(Collectors.toList());

        // 必要なタグを削除
        if (!tagsToRemove.isEmpty()) {
            userTagsMapper.deleteAllTagsByUserId(userId);
        }

        // 既存タグ＋新規追加タグの合計が6を超えないよう制御
        int remainingSlots = 6 - existingTags.size();
        List<UserTags> userTags = tagsToAdd.stream()
            .limit(remainingSlots) // 既存タグを考慮し、合計6つまで制限
            .map(tagId -> new UserTags(userId, tagId))
            .collect(Collectors.toList());

        // 必要なタグを追加
        if (!userTags.isEmpty()) {
            userTagsMapper.insertUserTags(userTags);

        }
	    }
		@Override
		public List<String> getTagNamesForUser(int userId) {
	    return  userTagsMapper.findTagNamesByUserId(userId);
	}

		
		

}
