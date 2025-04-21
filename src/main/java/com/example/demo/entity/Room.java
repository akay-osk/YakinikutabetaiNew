package com.example.demo.entity;

import lombok.Data;

/*
 * チャットルーム用エンティティ
 * もしかしなくてもチャットルーム内で使わないかも（）
 * 作成者　森川
 */

@Data
public class Room{
    private int room_id;
    private int user_id;
    private String delete_at;
    private int matching_id;
}