package com.example.demo.entity;

import java.time.LocalDate;

import lombok.Data;

/*
 * マッチング用エンティティ
 * 作成者　奥野
 */
@Data
public class Matching {
	private int matching_id;
	private int user_id;
	private LocalDate matching_day;
	private String matching_time;
	private boolean matching_gender;
	private int matching_min_age;
	private int matching_max_age;
	private boolean matching_member;
	private String matching_area;
 
	

}


