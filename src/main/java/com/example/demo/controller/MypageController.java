package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.PreUser;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.UserTagsService;
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
	@Autowired
	private UserTagsService userTagsService;
	

	//プロフィール情報を表示する
	@GetMapping("/mypage")
	public String showMypage(Model model) {

		//現在ログインしているユーザーのIDを取得する
		int currentUserId = userService.getCurrentUserId();
		User currentUser = userService.findByIdUsers(currentUserId);

		model.addAttribute("userId", currentUser.getUser_id());
		model.addAttribute("username", currentUser.getUser_name());
		model.addAttribute("introComment", currentUser.getUser_detail());
		model.addAttribute("favoritePart", currentUser.getUser_likes());

		//タグの取得
		List<String> tagNames = currentUser.getTags().stream()
				.map(Tag::getTag_name)
				.collect(Collectors.toList());
		//デバッグ用
		System.out.println(currentUser.getTags());

		model.addAttribute("tags", tagNames);

		model.addAttribute("profileImageBase64", currentUser.getUser_icon());//フロントで画像処理お願いします。

		return "Mypage";

	}

	//プロフィールを編集画面を表示(仮)
		@GetMapping("/edit")
		public String showEditProfilePage(Model model, Principal principal) {
			String username= principal.getName();
			Optional<User> optionalUser = userService.findByUsername(username);
			User user = optionalUser.orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
			model.addAttribute("user", user);
			return "UserProfileEdit";
		}

	//編集されたプロフィールの情報を更新する(仮)
		@PostMapping("/edit")
		public String updateProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails,@ModelAttribute PreUser preUser ) {
			User user = userService.findByIdUsers(customUserDetails.getUserId());
			user.setUser_name(preUser.getUser_name());
			user.setNewPassword(preUser.getNewPassword());
			user.setUser_age(preUser.getUser_age());
			user.setUser_gender(Boolean.parseBoolean(preUser.getUser_gender()));
			user.setUser_detail(preUser.getUser_detail());
			user.setUser_icon("data:image/jpeg;base64,"+preUser.getUser_icon());
		    userService.updateUsers(user);
			userTagsService.saveUserTags(customUserDetails.getUserId(), preUser.getTags());
			return "redirect:/mypage";
		}

	//アカウント削除を処理(仮)
	@PostMapping("/delete-account")
	public String deleteAccount() {

		//トップ画面に遷移
		return "redirect:/";
	}

}
