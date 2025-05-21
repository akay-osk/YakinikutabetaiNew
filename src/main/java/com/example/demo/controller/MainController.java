package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.LoginForm;
import com.example.demo.dto.PreUser;
import com.example.demo.entity.User;
import com.example.demo.service.UserTagsService;
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
	@Autowired
	private UserTagsService userTagsService;
	
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
	public String processRegistration(@ModelAttribute PreUser preUser) {
		User user = new User();
		user.setUser_name(preUser.getUser_name());
		user.setNewPassword(preUser.getNewPassword());
		user.setUser_age(preUser.getUser_age());
		user.setUser_gender(Boolean.parseBoolean(preUser.getUser_gender()));
		user.setUser_detail(preUser.getUser_detail());
		user.setUser_icon("data:image/jpeg;base64,"+preUser.getUser_icon());
		System.out.println(user);
	    usersService.insertUsers(user);
	    Optional<User> ouser = usersService.findByUsername(preUser.getUser_name());
		userTagsService.saveUserTags(ouser.get().getUser_id(), preUser.getTags());
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
