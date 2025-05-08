package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * HomeControllerクラス
 * ホーム画面と3つの機能
 * 作成者 奥田あかね
 */

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@GetMapping
	public String showHomePage(Model model) {
		
		//参加中マッチング、作成済マッチングリクエストの取得など
		return "Home";
	}
	
	@GetMapping("/current-matching")
	public String showCurrentMatching() {
		
		//引数で@ModelAttribute で Room entityの情報を受け取る必要？
		
		return "Chatroom"; //マッチング中のチャットルームに遷移
	}
	
	@GetMapping("/my-request-status")
	public String showRequestStatus(Model model) {
		
		//DBから自分のリクエストの状態取得
		return "request_status"; // <- ！！仮。動作しない！！
	}

}
