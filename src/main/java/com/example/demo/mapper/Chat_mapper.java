package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Chat;

/*
 * チャットルーム用マッパークラス
 * ※使いまわせる場合は要相談
 * 作成者　森川
 */

@Mapper
public interface Chat_mapper {
    //指定IDデータ取得
    Chat selectById(@Param("room_id") Integer id);
    //コメント書き込み
    void insert(Chat chat);
}
