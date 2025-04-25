package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Chat;

/*
 * チャットルーム用マッパー
 * 作成者　森川
 */
@Mapper
public interface Chat_mapper {
	
    //指定IDデータ取得
	@Select("SELECT user_name, chat_comment, create_at FROM chat WHERE room_id = #{room_id} ORDER BY create_at")
    Chat selectById(@Param("room_id") Integer id);
	
    //コメント書き込み
	@Insert("INSERT INTO chat (user_name, chat_comment, create_at) VALUES (#{user_name}, #{chat_comment}, CURRENT_TIMESTAMP)")
    void insert(Chat chat);
}
