package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.User;

/*
 * アイコン表示マッパー
 * 作成者　森川
 */

@Mapper
public interface Icon_mapper {
	@Select("SELECT user_icon, user_name FROM users WHERE user_id = #{user_id}")
	User selectByIcon(@Param("user_id") Integer id);
}
