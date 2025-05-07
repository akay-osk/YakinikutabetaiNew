package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Chat;
import com.example.demo.service.Chat_service;

import lombok.RequiredArgsConstructor;


/*
 * ChatControllerクラス
 * チャットルーム機能
 * 作成者 奥田
 * 編集者　森川
 */

@Controller
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatController {
	
	//チャットルームを表示
	@GetMapping
	public String showChatroom(Model model) {
		
		return "Chatroom";
	}
	
	//チャットルーム退出処理
	@PostMapping("/exit")
	public String exitChatroom() {
		
		return "redirect:/home";
	}

		private final Chat_service chat_service;
		
		//IDでチャット取得
		@GetMapping("/{id}")
		public ResponseEntity<Chat> getChatById(@PathVariable Integer id){
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
