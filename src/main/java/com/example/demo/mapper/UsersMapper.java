package com.example.demo.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.User;

/*
 * ユーザー用マッパー
 * 作成者　奥野
 */

@Mapper


public interface UsersMapper {
	
	//ユーザー情報新規登録
	@Insert("INSERT INTO users (user_pass,user_name,user_age,user_gender,user_likes,user_detail,user_icon,user_address) VALUES (#{user_pass}, #{user_name}, #{user_age}, #{user_gender}, #{user_likes}, #{user_detail}, #{user_icon}, #{user_address} )")
	@Options(useGeneratedKeys = true, keyProperty = "user_id")
	void insert(User users);
	
	//ユーザー情報更新
	@Update("UPDATE users SET user_pass = #{user_pass},user_name = #{user_name},user_age = #{user_age},user_gender = #{user_gender} ,user_likes = #{user_likes},user_detail = #{user_detail} ,user_icon =  #{user_icon} ,user_address = #{user_address} WHERE user_id =#{id}")
	void update(User users);

	//ユーザー情報削除
	@Delete("DELETE FROM users WHERE user_id = #{id}")
	void delete(Integer id);

	//ユーザー情報取得
	@Select("SELECT * FROM users WHERE user_id = #{id}")
	User selectByIdUsers(@Param("user_id") Integer id);

	//CustomUserDetails用
	@Select("SELECT * FROM users WHERE user_name = #{user_name}")
	User findByUsername(String username);
}
