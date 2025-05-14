package com.example.demo.entity;

import java.util.List;

import lombok.Data;

/*
 * チャットルーム用エンティティ
 * 作成者　森川
 */

@Data
public class Room{
    private int room_id; 
    private String delete_at;
    private boolean is_single;
    private boolean is_full;
    // RoomUserのリストを追加
    private List<RoomUser> roomUsers;
}