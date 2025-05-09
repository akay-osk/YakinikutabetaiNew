package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Chat;

/*
 * チャット用サービス
 * 作成者　森川
 */

public interface Chat_service {
    List<Chat> findByIdChat(int roomId);
    void insertChat(Chat chat);
}
