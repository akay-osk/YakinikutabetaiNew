package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Block;

/*
 * ブロック用マッパー
 * 作成者　奥野
 */
@Mapper
public interface BlockMapper {
	
	//ブロック登録
	  @Insert("INSERT INTO blocking (user_id, blocking_user_id) VALUES (#{user_id}, #{blocking_user_id})")
	    void insert(Block block);

	  
}
