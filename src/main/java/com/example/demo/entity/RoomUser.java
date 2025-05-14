package com.example.demo.entity;
/*
 * room対応userの中間テーブル用エンティティ
 * 作成者　奥野
 */

import lombok.Data;

@Data
public class RoomUser {
	private int room_id;
	private int user_id;
	private User user;

}
