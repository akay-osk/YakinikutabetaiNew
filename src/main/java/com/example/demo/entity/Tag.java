package com.example.demo.entity;
/*
 * タグ用エンティティ
 * 作成者　奥野
 */

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tag_id;
	private String tag_name;
	
	@ManyToMany(mappedBy= "tags")
	private List<User> users;

}
