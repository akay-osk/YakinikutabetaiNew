package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Room;

/*
 * ルームサービス
 * 作成者　森川
 * 編集　奥野5/9
*/

public interface Room_service {
    Room selectById(Integer roomId);
    void insert(Room room);
    void delete(int roomId);
    void addUserToRoom(int roomId, int userId);
    List<Integer> getUsersInRoom(int roomId);
	List<Room> getAllRooms();
	void updateRoom(Room room);

}