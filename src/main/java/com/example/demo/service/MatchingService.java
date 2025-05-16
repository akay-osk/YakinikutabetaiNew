package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.entity.Matching;

/*
 * マッチング用サービス
 * 作成者　奥野
 */
public interface MatchingService {
	
	//マッチング希望条件入力
	void insertMatching(Matching matching);
	
	//マッチング検索
	List<Matching> findMatching(
			LocalDate matching_day,
			String matching_time,
			boolean matching_gender,
			int minAge,
			int maxAge,
			boolean matching_member,
			String matching_area,
			int user_id
			);

	Matching findByUserId(int userId);
	
	void delete(Integer matchingId);


}
