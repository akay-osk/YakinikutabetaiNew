package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Matching;
import com.example.demo.entity.Room;
import com.example.demo.entity.RoomUser;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.MatchingService;
import com.example.demo.service.Room_service;
import com.example.demo.service.UsersService;

/*
 * MatchingControllerクラス
 * マッチング検索・結果処理
 * 作成者 奥田
 */

@Controller
public class MatchingController {
	
	/*
	 * 5/7追加　奥野
	 */
	@Autowired
	private MatchingService matchingService;
	
	
	@Autowired
	private Room_service roomService;
	
	@Autowired
	private UsersService usersService;
	
	//マッチング希望条件検索
	
	/*
	 *5/7 
	 *Matchingrequestを用意していたMatchingに変更しました
	 * 奥野
	 */
	


	    @GetMapping("/home")
	    
	    public String showHome(Model model) {
	    	int userId = usersService.getCurrentUserId();

	        boolean hasMatchingRoom = usersService.hasRoom(userId);
	        boolean isWaitingForMatch = usersService.hasWaitingCondition(userId);
	    	
	        model.addAttribute("matching", new Matching());
	        model.addAttribute("hasMatchingRoom", hasMatchingRoom); // or false
	        model.addAttribute("isWaitingForMatch", isWaitingForMatch); // or true
	        
	        // 🔽 マッチング中のルームIDを取得して渡す
	        Room room = roomService.findRoomByUserId(userId);
	        if (room != null) {
	            model.addAttribute("roomId", room.getRoom_id());
	        }
	        
	        return "Home";  // ← HTMLファイル名が Home.html の場合
	    }
		
	@PostMapping("/home")
	public String processSearch(@ModelAttribute Matching matching, Model model) {
		   // ログインユーザーのIDを取得（例）
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
	    int userId = userDetails.getUserId();
	    if (usersService.hasRoom(userId)) {
	        return "redirect:/home"; // またはエラーページに遷移させる
	    }
	    // matchingにセット
	    matching.setUser_id(userId);
	
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
				matching.getMatching_area(),
				userId
				
				);
		
		// 結果の表示
		if (!candidates.isEmpty()) {
			model.addAttribute("candidates", candidates);
			return "redirect:/select";
		} else {
			model.addAttribute("request", matching);
			model.addAttribute("notFoundReason", "条件に合うユーザーが見つかりませんでした。条件を緩めて再検索してください。");
			return "MatchingNotFound";
		}
	
	}
	
	@PostMapping("/chatroom")
	public String selectRoom(@RequestParam Integer selectedRoomId, Model model) {

	    // ログイン中のユーザー情報を取得
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	    Integer myUserId = userDetails.getUserId();

	    // ルームにユーザーを追加（まだ参加していなければ）
	    if (roomService.findRoomByUserId(myUserId) == null) {
	        roomService.addUserToRoom(selectedRoomId, myUserId);
	    }

	    // モデルにroomIdをセット（Thymeleafテンプレートで使うため）
	    model.addAttribute("roomId", selectedRoomId);

	    // チャットルームのThymeleafテンプレートに遷移
	    return "Chatroom";
	}


	@GetMapping("/select")
	public String showMatchingSucceededPage(Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	    Integer userId = userDetails.getUserId();
	    
	    List<Room> roomList = roomService.getAllRooms(); // 全ルーム取得でOK
	    model.addAttribute("roomList", roomList);
	    for (Room room : roomList) {
	        List<RoomUser> roomUsers = roomService.getRoomUser(room.getRoom_id());
	        room.setRoomUsers(roomUsers);
	    }
	    return "MatchingResult"; 
	}

	@GetMapping("/check-status")
	@ResponseBody
	public Map<String, Object> checkMatchingStatus(@RequestParam int userId) {
	    Map<String, Object> result = new HashMap<>();
	    
	    Room room = roomService.findRoomByUserId(userId); // 参加中のルームを取得（未実装なら作る必要あり）
	    
	    if (room != null) {
	        result.put("matched", true);
	        result.put("roomId", room.getRoom_id());
	    } else {
	        result.put("matched", false);
	    }
	    
	    return result;
	}

	@GetMapping("/notfound")
	public String showNotFoundPage(Model model) {
	    org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	    Integer userId = userDetails.getUserId();

	    model.addAttribute("user_id", userId);
	    
	    List<Room> roomList = roomService.getAllRooms();
	    model.addAttribute("roomList", roomList);
	    for (Room room : roomList) {
	        List<RoomUser> roomUsers = roomService.getRoomUser(room.getRoom_id());
	        for (RoomUser ru : roomUsers) {
	            System.out.println("User: " + ru.getUser().getUser_name() + ", Icon length: " 
	                + (ru.getUser().getUser_icon() == null ? "null" : ru.getUser().getUser_icon().length()));
	        }
	        room.setRoomUsers(roomUsers);
	    }
	    
	    return "MatchingNotFound";
	}

	
	//条件を変更してマッチングを再検索
	@PostMapping("/retry")
	public String retrySearch() {
		  // ログイン中のユーザー情報を取得
	    org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	    Integer userId = userDetails.getUserId();

	    // 現在のマッチング条件を削除
	    Matching matching = matchingService.findByUserId(userId);
	    if (matching != null) {
	        matchingService.delete(matching.getMatching_id());
	    }
	 
	    
		return "redirect:/home";
	}
	
	
	

//    @GetMapping("/select/succeded")
//    public String showMatchingResult(Model model) {
//        // ダミーデータを準備
//        List<Room> roomList = roomService.getAllRooms(); // DBからルームを取得
//        // ルームごとのユーザー情報を追加
//        for (Room room : roomList) {
//            List<RoomUser> roomUsers = roomService.getRoomUser(room.getRoom_id()); // 各ルームの参加ユーザーを取得
//            room.setRoomUsers(roomUsers); // ルームにユーザーをセット
//        }
//
//        // モデルにルームリストを追加
//        model.addAttribute("roomList", roomList);
//
//        return "MatchingResult"; // HTMLテンプレート名
//    }
}

