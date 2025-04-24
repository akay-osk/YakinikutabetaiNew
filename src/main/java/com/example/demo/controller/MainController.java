package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * MainControllerクラス
 * トップやログイン・登録の画面遷移を制御
 * 作成者 奥田
 */

@Controller
public class MainController {
	
	@GetMapping("/")
	public String showTopPage() {
		return "top";
	}

}
