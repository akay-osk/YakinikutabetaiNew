package com.example.demo.controller;
/*
 * アイコンポップアップユーザー情報表示コントローラー
 * 作成者　奥野
 * 
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Block;
import com.example.demo.entity.User;
import com.example.demo.entity.UserProfile;
import com.example.demo.service.BlockService;
import com.example.demo.service.UserTagsService;
import com.example.demo.service.UsersService;

@Controller
public class UserIconController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private UserTagsService userTagsService;
	@Autowired
	private BlockService blockService;
	
	
	@GetMapping("/user/icon/{userId}")
	@ResponseBody
	public ResponseEntity<String> getUserIcon(@PathVariable int userId) {
	    User user = usersService.findByIdUsers(userId);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_TYPE, "text/plain")
	        .body(user.getUser_icon());//String型に変更、表でBase64文字列から画像バイナリに変換して返す
	}

	
	@GetMapping("/user/detail/{userId}")
	@ResponseBody
	public UserProfile getUsersDetails(@PathVariable int userId) {
		User user =  usersService.findByIdUsers(userId);
		List<String> tagNames = userTagsService.getTagNamesForUser(user.getUser_id());
		return new UserProfile(
				user.getUser_name(),
				user.getUser_age(),
				user.isUser_gender(),
				user.getUser_likes(),
				user.getUser_detail(),
				tagNames
				);	
	}
	
	@PostMapping("/block")
	@ResponseBody
	public ResponseEntity<String> blockUser(@RequestParam int targetUserId) {
	    int currentUserId = usersService.getCurrentUserId(); // ログイン中のユーザーIDを取得（セッションやSecurityContextなどから）

	    // 同一ユーザーや既にブロック済みかのチェックはサービス層で行うのがベスト
	    Block block = new Block();
	    block.setUser_id(currentUserId);
	    block.setBlocking_user_id(targetUserId);

	    blockService.blockUser(block);

	    return ResponseEntity.ok("ユーザーをブロックしました");
	}

}
