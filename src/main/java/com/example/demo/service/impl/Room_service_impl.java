package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Room;
import com.example.demo.entity.RoomUser;
import com.example.demo.mapper.Room_mapper;
import com.example.demo.service.Room_service;

import lombok.RequiredArgsConstructor;

/*
 * ルームサービス実装
 * 作成者　森川
 * 
 * 編集　奥野5/9
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
		room_mapper.insertRoom(room);
	}

	@Override
	public void delete(int roomId) {
		room_mapper.deleteRoom(roomId);
	}
	
	@Override
	public void addUserToRoom(int roomId, int userId) {
		room_mapper.insertRoomUser(roomId, userId);
	}
	
	@Override
	public List<Integer> getUsersInRoom(int roomId){
		return room_mapper.selectUserIdsInRoom(roomId);
	}

	@Override
	public List<Room> getAllRooms() {
		return room_mapper.selectAllRooms();
	}
	
	@Override
	public void updateRoom(Room room) {
	    room_mapper.updateRoom(room);
	}
	
	@Override
	public Room findRoomByUserId(int userId) {
	    return room_mapper.findRoomByUserId(userId);
	}

	@Override
	public void exitByUserId(int userId) {
	    // ユーザーが所属するルームを取得
	    Room room = room_mapper.findRoomByUserId(userId);

	    // もしユーザーがルームに所属していなければ、何もしない
	    if (room == null) {
	        // ユーザーがルームに所属していない場合の処理
	        throw new IllegalArgumentException("指定されたユーザーはルームに所属していません。");
	    }

	    // そのユーザーをルームから削除
	    room_mapper.deleteByUserId(userId);

	    // そのルームに残っているユーザーを確認
	    List<Integer> remainingUsers = room_mapper.selectUserIdsInRoom(room.getRoom_id());

	    // 残っているユーザーがいなければ、ルームを削除
	    if (remainingUsers.isEmpty()) {
	        room_mapper.deleteRoom(room.getRoom_id());
	    }
	}

	@Override
	public List<RoomUser> getRoomUser(Integer roomId) {
		return room_mapper.findByRoomId(roomId);
	}
}
