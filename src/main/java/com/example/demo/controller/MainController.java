package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.LoginForm;
import com.example.demo.entity.User;
import com.example.demo.service.UsersService;

/*
 * MainControllerクラス
 * トップやログイン・登録の画面遷移を制御
 * 作成者 奥田
 */

@Controller
public class MainController {
	
	@Autowired
	private UsersService usersService;
	
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
	public String showRegistrationPage(Model model) {
		model.addAttribute("user", new User());
		return "Register";
	}
	
	@PostMapping("/register")
	public String processRegistration(@ModelAttribute User user) {
		//ユーザー登録処理
		usersService.insertUsers(user);
		
		return "redirect:/login";
	}

	//変更byナカムラ(securityと競合するため)
/*	@PostMapping("/login")
	public String processLogin(@RequestParam String userId, @RequestParam String password, Model model) {
		
		//ログイン処理
		
		//if (認証成功)
		return "redirect:/home";
		
		//else
		//return "Login";	//エラー表示
	}
*/
}
