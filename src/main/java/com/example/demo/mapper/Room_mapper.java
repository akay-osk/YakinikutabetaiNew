package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.Room;

/*
 * ルームマッパー
 * 作成者　森川
 * 
 * 編集　奥野 5/9
 */

@Mapper
public interface Room_mapper {
	
	@Select("SELECT * FROM room WHERE room_id = #{id}")
	Room selectById(@Param("id") Integer id);
	
	@Insert("INSERT INTO room(user_id, is_single, is_full, delete_at) VALUES(#{user_id}, #{is_single}, #{is_full}, #{delete_at})")
	@Options(useGeneratedKeys=true, keyProperty="room_id")
	void insertRoom(Room room);
	
	@Delete("DELETE FROM room WHERE room_id = #{room_id}")
	void deleteRoom(@Param("room_id") int roomId);

	@Insert("INSERT INTO room_user(room_id, user_id) VALUES (#{roomId}, #{userId})")
	void insertRoomUser(int roomId, int userId);

	@Select("SELECT user_id FROM room_user WHERE room_id = #{roomId}")
	List<Integer> selectUserIdsInRoom(@Param("roomId") int roomId);

	@Select("SELECT * FROM room WHERE is_full = false")
	List<Room> selectAllRooms();
	
	@Update("UPDATE room SET is_single = #{is_single}, is_full = #{is_full}, delete_at = #{delete_at} WHERE room_id = #{room_id}")
	void updateRoom(Room room);
	
	@Select("SELECT r.* FROM room r JOIN room_user ru ON r.room_id = ru.room_id WHERE ru.user_id = #{userId} ORDER BY r.room_id DESC LIMIT 1")
		Room findRoomByUserId(@Param("userId") int userId);

	@Delete("DELETE FROM room_user WHERE user_id = #{userId}")
	void deleteByUserId(@Param("userId") int userId);
}
