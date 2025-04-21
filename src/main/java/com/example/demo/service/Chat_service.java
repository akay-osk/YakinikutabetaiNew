package com.example.demo.service;

import com.example.demo.entity.Chat;

/*
 * チャットルーム用サービス
 * 作成者　森川
 */

public interface Chat_service {
    Chat findByIdChatComment(Integer id);
    void insertChat(Chat chat);
}
