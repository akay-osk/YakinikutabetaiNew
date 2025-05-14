package com.example.demo.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatController {

	private final Chat_service chat_service;
	private final Room_service room_service;

	//ユーザー名を取得
	@GetMapping("/getUserName")
	public String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated()) {
			throw new RuntimeException("認証されていないユーザーです");
		}
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		return userDetails.getUsername();
	}

	//チャットを新規作成
	@PostMapping("/insert")
	public ResponseEntity<?> createChat(@Valid @RequestBody Chat chat, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body("無効なチャットデータです");
		}
		chat_service.insertChat(chat);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	//チャット履歴取得
	@GetMapping("/history")
	public ResponseEntity<List<Chat>> getChatByRoomId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		int userId = userDetails.getUserId();
		Room room = room_service.findRoomByUserId(userId);
		if (room == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		List<Chat> chat = chat_service.findByIdChat(room.getRoom_id());
		if (chat.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(chat);
	}

	//ユーザーがチャットルームから退出
	@PostMapping("/exit")
	public ResponseEntity<String> exitRoom() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		int userId = userDetails.getUserId();
		String username = userDetails.getUsername();
		Room room = room_service.findRoomByUserId(userId);

		if (room != null) {
			room_service.exitByUserId(userId);
			return ResponseEntity.ok(username + " が退出しました。");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("チャットルームが見つかりません。");
		}
	}
}