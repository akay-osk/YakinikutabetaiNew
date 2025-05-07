package com.example.demo.service;

import com.example.demo.entity.Chat;

/*
 * チャット用サービス
 * 作成者　森川
 */

public interface Chat_service {
    Chat findByIdChat(Integer id);
    void insertChat(Chat chat);
}
