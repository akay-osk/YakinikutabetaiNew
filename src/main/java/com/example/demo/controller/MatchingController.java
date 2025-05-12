package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Matching;
import com.example.demo.entity.Room;
import com.example.demo.service.MatchingService;
import com.example.demo.service.Room_service;

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
	
	
	@Autowired
	private Room_service roomService;
	
	//マッチング希望条件検索
	
	/*
	 *5/7 
	 *Matchingrequestを用意していたMatchingに変更しました
	 * 奥野
	 */
	
	// 検索フォームを表示するGETメソッドを追加
	@GetMapping("/form")
	public String showMatchingForm(Model model) {
	    model.addAttribute("matching", new Matching()); // 空のMatchingオブジェクトを渡す
	    return "MatchingSearch"; // templatesに置いたHTMLファイル名（拡張子は不要）
	}

	
	
	
	
	@PostMapping("/search")
	public String processSearch(@ModelAttribute Matching matching, Model model) {
		
		//入力したマッチング条件をDBに保存
		matchingService.insertMatching(matching);
		
		//検索実行
		List<Matching> candidates = matchingService.findMatching(
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
			model.addAttribute("notFoundReason", "条件に合うユーザーが見つかりませんでした。条件を緩めて再検索してください。");
			return "MatchingNotFound";
		}
	
	}
	
//	
//	//マッチング候補から一つを選択し、チャットルームに参加する
//	@PostMapping("/select")
//	public String selectCandidate(@RequestParam Integer candidateId, @RequestParam Integer myUserId) {
//		
//		//すでに2人が参加しているルームがあるか確認
//		List<Room> allRooms = roomService.getAllRooms();
//		Room existingRoom = null;
//		
//		for(Room r : allRooms) {
//			List<Integer> users = roomService.getUsersInRoom(r.getRoom_id());
//			if(users.contains(myUserId) && users.contains(candidateId) && users.size() == 2) {
//				existingRoom = r;
//				break;
//			}
//		}
//		
//		Room room;
//		 if (existingRoom != null) {
//		        room = existingRoom;
//		    } else {
//		        // 新しいチャットルーム作成
//		        room = new Room();
//		        room.set_single(true);
//		        room.set_full(true);
//		        room.setDelete_at(null);
//		        room.setMatching_id(0); // 必要に応じて設定
//
//		        roomService.insert(room);
//
//		        // 2人をroom_userに登録
//		        roomService.addUserToRoom(room.getRoom_id(), myUserId);
//		        roomService.addUserToRoom(room.getRoom_id(), candidateId);
//		    }
//
//		    return "redirect:/Chatroom?room_id=" + room.getRoom_id();
//	
//	}
	
	@PostMapping("/select")
	public String selectCandidate(@RequestParam Integer candidateId, @RequestParam Integer myUserId) {

	    // ユーザーの希望条件から「1対1 or 複数人」を判断
	    boolean isGroup = matchingService.findByUserId(myUserId).isMatching_member();

	    List<Room> allRooms = roomService.getAllRooms();
	    Room room = null;

	    if (!isGroup) {
	        // ---------- 1対1 ----------
	        for (Room r : allRooms) {
	            if (r.is_single()) {
	                List<Integer> users = roomService.getUsersInRoom(r.getRoom_id());
	                if (users.contains(myUserId) && users.contains(candidateId) && users.size() == 2) {
	                    room = r;
	                    break;
	                }
	            }
	        }

	        if (room == null) {
	            room = new Room();
	            room.set_single(true);
	            room.set_full(true);
	            room.setDelete_at(null);         
	            roomService.insert(room);
	            roomService.addUserToRoom(room.getRoom_id(), myUserId);
	            roomService.addUserToRoom(room.getRoom_id(), candidateId);
	        }

	    } else {
	        // ---------- 複数人 ----------
	        for (Room r : allRooms) {
	            if (!r.is_single() && !r.is_full()) {
	                List<Integer> users = roomService.getUsersInRoom(r.getRoom_id());
	                if (!users.contains(myUserId)) {
	                    roomService.addUserToRoom(r.getRoom_id(), myUserId);
	                    // 満員チェック（例: 50人）→フラグ更新必要
	                    if (users.size() + 1 >= 50) {
	                        r.set_full(true);
	                        roomService.updateRoom(r); // updateRoomメソッド要実装
	                    }
	                    room = r;
	                    break;
	                }
	            }
	        }

	        if (room == null) {
	            room = new Room();
	            room.set_single(false);
	            room.set_full(false);
	            room.setDelete_at(null);
	      
	            roomService.insert(room);
	            roomService.addUserToRoom(room.getRoom_id(), myUserId);
	        }
	    }

	    return "redirect:/Chatroom?room_id=" + room.getRoom_id();
	}

	
	
	//条件を変更してマッチングを再検索
	@PostMapping("/retry")
	public String retrySearch() {
		
		return "redirect:/matching/search";
	}
}

