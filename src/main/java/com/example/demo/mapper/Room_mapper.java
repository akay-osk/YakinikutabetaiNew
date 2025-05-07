package com.example.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Room;

/*
 * ルームマッパー
 * 作成者　森川
 */

@Mapper
public interface Room_mapper {
	
	@Select("SELECT * FROM room WHERE room_id = #{room_id}")
	Room selectById(Integer id);
	
	@Insert("INSERT INTO room(user_id, is_single, is_full, delete_at, matching_id) VALUES(#{user_id}, #{is_single}, #{is_full}, #{delete_at}, #{matching_id})")
	@Options(useGeneratedKeys=true, keyProperty="room_id")
	void insert(Room room);
	
	@Delete("DELETE FROM room WHERE room_id = #{room_id}")
	void delete(@Param("room_id") int roomId);
}
