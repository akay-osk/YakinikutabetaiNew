package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.mapper.Icon_mapper;
import com.example.demo.service.Icon_service;

import lombok.RequiredArgsConstructor;

/*
 * アイコン表示サービス実装
 * 作成者　森川
 */

@Service
@RequiredArgsConstructor
public class Icon_service_impl implements Icon_service{
	private final Icon_mapper icon_mapper;
	
	@Override
	public User selectByIcon(Integer id) {
		return icon_mapper.selectByIcon(id);
	}
}
