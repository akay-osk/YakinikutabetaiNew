package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Room;
import com.example.demo.mapper.Room_mapper;
import com.example.demo.service.Room_service;

import lombok.RequiredArgsConstructor;

/*
 * ルームサービス実装
 * 作成者　森川
 */
@Service
@Transactional
@RequiredArgsConstructor
public class Room_service_impl implements Room_service{
	private final Room_mapper room_mapper;
	@Override
	public Room selectById(Integer id) {
		return room_mapper.selectById(id);
	}

	@Override
	public void insert(Room room) {
		room_mapper.insert(room);
	}

	@Override
	public void delete(int roomId) {
		room_mapper.delete(roomId);
	}

}
