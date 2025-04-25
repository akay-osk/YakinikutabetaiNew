package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * ChatControllerクラス
 * チャットルーム機能
 * 作成者 奥田
 */

@Controller
@RequestMapping("/chatroom")
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

}
