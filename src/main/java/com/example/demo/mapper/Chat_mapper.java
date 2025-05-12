package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Chat;

/*
 * チャット用マッパー
 * 作成者　森川
 */
@Mapper
public interface Chat_mapper {
	
    //指定IDデータ取得
	@Select("SELECT user_name, chat_comment, create_at FROM chat WHERE room_id = #{room_id} ORDER BY create_at")
    List<Chat> selectById(@Param("roomId") int roomId);
	
    //コメント書き込み
	@Insert("INSERT INTO chat (user_name, chat_comment, create_at) VALUES (#{user_name}, #{chat_comment}, CURRENT_TIMESTAMP)")
    void insert(Chat chat);
}
