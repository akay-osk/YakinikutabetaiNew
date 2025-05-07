package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Matching;
import com.example.demo.service.MatchingService;

/*
 * MatchingControllerクラス
 * マッチング検索・結果処理
 * 作成者 奥田
 */

@Controller
@RequestMapping("/matching")
public class MatchingController {
	
	/*
	 * 5/7追加　奥野
	 */
	@Autowired
	private MatchingService matchingService;
	
	
	
	//マッチング希望条件検索
	
	/*
	 *5/7 
	 *Matchingrequestを用意していたMatchingに変更しました
	 * 奥野
	 */
	@PostMapping("/search")
	public String processSearch(@ModelAttribute Matching matching, Model model) {
		
		//入力したマッチング条件をDBに保存
		matchingService.insertMatching(matching);
		
		//検索実行
		List<Matching> candidates = matchingService.findMatching(
				matching.getUser_id(),
				matching.getMatching_day(),
				matching.getMatching_time(),
				matching.isMatching_gender(),
				matching.getMatching_min_age(),
				matching.getMatching_max_age(),
				matching.isMatching_member(),
				matching.getMatching_area()
				);
		
		// 結果の表示
		if (!candidates.isEmpty()) {
			model.addAttribute("candidates", candidates);
			return "MatchingResult";
		} else {
			model.addAttribute("request", matching);
			return "MatchingNotFound";
		}
	
	}
	
	
	//マッチング候補から一つを選択し、チャットルームに参加する
	@PostMapping("/select")
	public String selectCandidate(@RequestParam Integer candidateId) {
		
		//チャットルーム作成・参加
		return "redirect:/Chatroom";
	}
	
	
	//条件を変更してマッチングを再検索
	@PostMapping("/retry")
	public String retrySearch() {
		
		return "redirect:/matching/search";
	}
}

