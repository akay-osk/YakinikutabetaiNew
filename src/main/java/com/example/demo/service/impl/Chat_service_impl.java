package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Chat;
import com.example.demo.mapper.Chat_mapper;
import com.example.demo.service.Chat_service;

import lombok.RequiredArgsConstructor;

/*
 * チャットルーム用サービス実装
 * 作成者　森川
 */

@Service
@Transactional
@RequiredArgsConstructor
public class Chat_service_impl implements Chat_service{
	private final Chat_mapper chat_mapper;

	@Override
	public Chat findByIdChatComment(Integer id) {
		return chat_mapper.selectById(id);
	}

	@Override
	public void insertChat(Chat chat) {
		chat_mapper.insert(chat);
	}
	
}
