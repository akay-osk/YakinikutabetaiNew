package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.test.User;

/*
 * ProfileControllerクラス
 *マイページ、プロフィール更新など
 * 作成者 奥田
 */

@Controller
@RequestMapping("/mypage")
public class ProfileController {
	
	//プロフィール情報を表示する
	@GetMapping
	public String showMyPage(Model model) {
		
		//プロフィール情報取得
		return "Mypage";
	}
	
	//プロフィールを編集画面を表示
	@GetMapping("/edit")
	public String showEditProfilePage(Model model) {
		
		return "UserProfileEditPage";
	}
	
	//編集されたプロフィールの情報を更新する
	@PostMapping("/edit")
	public String updateProfile(@ModelAttribute User user) {
		
		return "redirect:/mypage";
	}
	
	//アカウント削除を処理
	@PostMapping("/delete-account")
	public String deleteAccount() {
		
		//トップ画面に遷移
		return "redirect:/";
	}

}
