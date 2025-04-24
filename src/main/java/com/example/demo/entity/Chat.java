package com.example.demo.entity;

import java.time.LocalDateTime;

import lombok.Data;

/*
 * チャット用エンティティ
 * 作成者森川
 */

@Data
public class Chat{
    private int room_id;
    private int user_id;
    private String user_name;
    private String chat_comment;
    private LocalDateTime create_at;
}