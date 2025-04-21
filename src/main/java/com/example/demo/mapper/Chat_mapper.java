package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Chat;

/*
 * チャットルーム用マッパー
 * 作成者　森川
 */
@Mapper
@RequestMapping("api/chat")
public interface Chat_mapper {
	
    //指定IDデータ取得
	@Select("SELECT * FROM chat WHERE room_id = #{room_id}")
    Chat selectById(@Param("room_id") Integer id);
	
    //コメント書き込み
	@Insert("INSERT INTO chat (user_name, chat_comment, created_at) VALUES (#{user_name}, #{chat_comment}, CURRENT_TIMESTAMP)")
    void insert(Chat chat);
}
