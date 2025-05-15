//package com.example.demo.controller;
//
//import java.util.Base64;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import com.example.demo.entity.User;
//import com.example.demo.service.impl.UsersServiceImpl;
//
///*
// * MypageControllerクラス
// * マイページでプロフィール情報を表示、編集画面/ログアウト/アカウント削除 へ遷移
// * 作成者 奥田
// */
//
//@Controller
//public class MypageController {
//	
//	@Autowired
//	private UsersServiceImpl userService;
//	
//	@GetMapping("/mypage")
//	public String showMypage(Model model) {
//		
//		//仮に現在ログインしているユーザーIDが取得済みとする
//		int currentUserId = userService.getCurrentUserId();
//		User user = userService.findByIdUsers(currentUserId);
//		
//		model.addAttribute("userId", user.getUser_id());
//		model.addAttribute("username", user.getUser_name());
//		model.addAttribute("introComment", user.getUser_detail());
//		model.addAttribute("favoritePart", user.getUser_likes());
//		model.addAttribute("tags", user.getTag_id());	//List<Integer>
//		
//		if(user.getUser_icon() != null && user.getUser_icon().length > 0) {
//			String base64Image = Base64.getEncoder().encodeToString(user.getUser_icon());
//			model.addAttribute("profileImageBase64", base64Image);
//		}else {
//			model.addAttribute("profileImageBase64", null);		//null対応はデフォルト画像用意する？
//		}
//		
//		return "Mypage";
//		
//	}
//}
