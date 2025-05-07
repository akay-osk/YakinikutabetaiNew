package com.example.demo.controller;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Chat;
import com.example.demo.entity.Room;
import com.example.demo.service.Chat_service;

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

	private final Set<String> users = ConcurrentHashMap.newKeySet();

	//チャットルーム入出処理
	@PostMapping("/join")
	public ResponseEntity<String> joinRoom(@RequestBody Room room, @RequestParam int currentMemberCount) {
	    if (room.is_single()) { // 1対1ルームの判定
	        if (currentMemberCount < 2) {
	            return ResponseEntity.ok("1対1ルームに参加しました");
	        }
	    } else { // 多人数ルーム判定
	        if (!room.is_full()) {
	            return ResponseEntity.ok("多人数ルームに参加しました");
	        }
	    }
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ルームは満室です");
	}

	//チャットルーム退出処理
	@PostMapping("/exit")
	public ResponseEntity<String> exitRoom(@RequestParam String username) {
		users.remove(username);
		return ResponseEntity.ok(username + " が退出しました。現在の人数: " + users.size());
	}

	//入出数カウント
	@GetMapping("/count")
	public ResponseEntity<Integer> getUserCount() {
		return ResponseEntity.ok(users.size());
	}

	private final Chat_service chat_service;

	//IDでチャット取得
	@GetMapping("/{id}")
	public ResponseEntity<Chat> getChatById(@PathVariable Integer id) {
		Chat chat = chat_service.findByIdChat(id);
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