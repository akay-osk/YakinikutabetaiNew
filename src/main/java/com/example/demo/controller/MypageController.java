package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.service.impl.UsersServiceImpl;

/*
 * MypageControllerクラス
 * マイページでプロフィール情報を表示、編集画面/ログアウト/アカウント削除 へ遷移
 * 作成者 奥田
 */

@Controller
public class MypageController {
	
	@Autowired
	private UsersServiceImpl userService;
	
	//プロフィール情報を表示する
	@GetMapping("/mypage")
	public String showMypage(Model model) {
		
		//仮に現在ログインしているユーザーIDが取得済みとする
		int currentUserId = userService.getCurrentUserId();
		User currentUser = userService.findByIdUsers(currentUserId);
		
		System.out.println("Base64 profile image: " + currentUser.getUser_icon());
		
		model.addAttribute("userId", currentUser.getUser_id());
		model.addAttribute("username", currentUser.getUser_name());
		model.addAttribute("introComment", currentUser.getUser_detail());
		model.addAttribute("favoritePart", currentUser.getUser_likes());
		model.addAttribute("tags", currentUser.getTag_id());	//List<Integer>
		model.addAttribute("profileImageBase64", currentUser.getUser_icon());//フロントで画像処理お願いします。
		
		return "Mypage";
		
	}
	
	//プロフィールを編集画面を表示(仮)
	@GetMapping("/edit")
	public String showEditProfilePage(Model model) {
//		model.addAttribute("user", currentUser);
		return "UserProfileEdit";
	}
	
	//編集されたプロフィールの情報を更新する(仮)
	@PostMapping("/edit")
	public String updateProfile(@ModelAttribute User user) {
			
		return "redirect:/mypage";
	}
	
	//アカウント削除を処理(仮)
	@PostMapping("/delete-account")
	public String deleteAccount() {
			
		//トップ画面に遷移
		return "redirect:/";
	}
	
}
