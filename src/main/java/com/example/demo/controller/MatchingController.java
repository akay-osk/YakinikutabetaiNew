package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.MatchingRequest;

/*
 * MatchingControllerクラス
 * マッチング検索・結果処理
 * 作成者 奥田
 */

@Controller
@RequestMapping("/matching")
public class MatchingController {
	
	//マッチング希望条件検索
	@PostMapping("/search")
	public String processSearch(@ModelAttribute MatchingRequest request, Model model) {
		
		//DB検索処理
		boolean found= true;	//仮
		if(found) {
			model.addAttribute("candidates", List.of(/* 候補 */));
			return "MatchingResult";
		}else {
			model.addAttribute("request", request);
			return "MatchingNotFound";
		}
	}
	
	//マッチング候補から一つを選択し、チャットルームに参加する
	@PostMapping("/select")
	public String selectCandidate(@RequestParam Integer candidateId) {
		
		//チャットルーム作成・参加
		return "redirect:/Chatroom";
	}
	
	//検索内容を保存しマッチングリクエストを作成
	@PostMapping("/save-request")
	public String saveRequest(@ModelAttribute MatchingRequest request) {
		
		//リクエストをDB保存
		return "redirect:/home";
	}
	
	//条件を変更してマッチングを再検索
	@PostMapping("/retry")
	public String retrySearch() {
		
		return "redirect:/matching/search";
	}
}
