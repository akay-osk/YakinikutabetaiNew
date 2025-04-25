package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.LoginForm;
import com.example.demo.test.User;

/*
 * MainControllerクラス
 * トップやログイン・登録の画面遷移を制御
 * 作成者 奥田
 */

@Controller
public class MainController {
	
	//トップ画面表示 
	@GetMapping("/")
	public String showTopPage() {
		return "TopPage";
	}
	
	//ログイン画面表示 
	//キタガワ作
	@GetMapping("/login")
	public String loginForm(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "Login";
	}
	
	//アカウント新規登録画面表示
	@GetMapping("/register")
	public String showRegistrationPage() {
		return "Register";
	}
	
	@PostMapping("/register")
	public String processRegistration(@ModelAttribute User user) {
		
		//ユーザー登録処理
		//userService.save(user);
		
		return "redirect:/login";
	}
	
	@PostMapping("/login")
	public String processLogin(@RequestParam String userId, @RequestParam String password, Model model) {
		
		//ログイン処理
		
		//if (認証成功)
		return "redirect:/home";
		
		//else
		//return "Login";	//エラー表示
	}

}
