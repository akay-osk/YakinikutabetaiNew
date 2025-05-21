package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Chat;
import com.example.demo.entity.Room;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.Chat_service;
import com.example.demo.service.MatchingService;
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
    private final MatchingService matchingService;


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
    public String sendMessage(@RequestParam("chat_comment") String chatComment) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        int userId = userDetails.getUserId();

        Room room = room_service.findRoomByUserId(userId);
        if (room != null) {
            Chat chat = new Chat();
            chat.setRoom_id(room.getRoom_id());
            chat.setUser_id(userId);
            chat.setUser_name(userDetails.getUsername());
            chat.setChat_comment(chatComment);
            chat.setCreate_at(LocalDateTime.now());
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

        matchingService.delete(matchingService.findByUserId(userId).getMatching_id());
        
        room_service.exitByUserId(userId);
        return "redirect:/home"; // ホーム画面へリダイレクト
    }
}