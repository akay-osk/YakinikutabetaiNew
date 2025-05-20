package com.example.demo.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Chat;
import com.example.demo.entity.Room;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.Chat_service;
import com.example.demo.service.Room_service;

import lombok.RequiredArgsConstructor;

/*
 * ChatControllerクラス
 * チャットルーム機能
 * 作成者 奥田
 * 編集者　森川
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/chatroom")
public class ChatController {

    private final Chat_service chat_service;
    private final Room_service room_service;

    // チャットルーム画面表示（履歴読み込み）
    @GetMapping
    public String showChatroom(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        int userId = userDetails.getUserId();
        String userName = userDetails.getUsername();

        Room room = room_service.findRoomByUserId(userId);
        if (room == null) {
            model.addAttribute("error", "チャットルームが見つかりません。");
            return "error";
        }

        List<Chat> messages = chat_service.findByIdChat(room.getRoom_id());

        model.addAttribute("userName", userName);
        model.addAttribute("messages", messages);
        return "chatroom"; // templates/chatroom.html
    }

    // メッセージ送信
    @PostMapping("/send")
    public String sendMessage(@Valid @ModelAttribute Chat chat, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/chatroom"; // エラーがあっても戻る（必要ならバリデーションエラーを画面に出す）
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        int userId = userDetails.getUserId();

        Room room = room_service.findRoomByUserId(userId);
        if (room != null) {
            chat.setRoom_id(room.getRoom_id());
            chat.setUser_id(userId);
            chat.setUser_name(userDetails.getUsername());
            chat_service.insertChat(chat);
        }

        return "redirect:/chatroom";
    }

    // 退出処理
    @PostMapping("/exit")
    public String exitRoom() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        int userId = userDetails.getUserId();

        room_service.exitByUserId(userId);
        return "redirect:/home"; // ホーム画面へリダイレクト
    }
}