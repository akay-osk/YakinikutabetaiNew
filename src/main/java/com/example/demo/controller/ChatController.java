package com.example.demo.controller;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	// Mapでルームごとのユーザー管理へ
	private final ConcurrentHashMap<String, Set<String>> roomUsers = new ConcurrentHashMap<>();

	private final Chat_service chat_service;
	private final Room_service room_service;

	// チャットルーム入室処理
	@PostMapping("/join")
	public ResponseEntity<String> joinRoom() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		int userId = userDetails.getUserId();
		int roomId = userDetails.getRoomId();

		
		Room room = room_service.selectById(roomId);
		
		if (room == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ルームが存在しません");
		}

		// 満室判定
		if (!room.is_full()) {
			// roomId を String 型に変換して使用
			String roomIdString = String.valueOf(roomId);
			roomUsers.putIfAbsent(roomIdString, ConcurrentHashMap.newKeySet());
			Set<String> usersInRoom = roomUsers.get(roomIdString);
			usersInRoom.add(String.valueOf(userId)); // userId も String 型に変換して使用
			return ResponseEntity.ok("ルームに参加しました");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ルームは満室です");
		}
	}

	// チャットルーム退出処理
	@PostMapping("/exit")
	public ResponseEntity<String> exitRoom() {
		// SecurityContextHolder からユーザー情報取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		int userId = userDetails.getUserId();
		int roomId = userDetails.getRoomId();

		// roomId を String 型に変換して使用@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		String roomIdString = String.valueOf(roomId);
		Set<String> usersInRoom = roomUsers.get(roomIdString);
		if (usersInRoom != null) {
			usersInRoom.remove(String.valueOf(userId)); // userId を String 型に変換して使用
			return ResponseEntity.ok(userId + " が退出しました。現在の人数: " + usersInRoom.size());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ルームが存在しません");
	}

	// IDでチャット取得
	@GetMapping("/{id}")
	public ResponseEntity<List<Chat>> getChatById(@PathVariable Integer id) {

		List<Chat> chat = chat_service.findByIdChat(id);
		if (chat != null) {
			return ResponseEntity.ok(chat);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// チャットを新規書き込み
	@PostMapping("/insert")
	public ResponseEntity<Void> createChat(@RequestBody Chat chat) {
		chat_service.insertChat(chat);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
