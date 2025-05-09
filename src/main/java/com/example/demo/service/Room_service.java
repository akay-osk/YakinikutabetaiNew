package com.example.demo.service;

import com.example.demo.entity.Room;

/*
 * ルームサービス
 * 作成者　森川 
 */

public interface Room_service {
    Room selectById(Integer id);
    void insert(Room room);
    void delete(int roomId);
}