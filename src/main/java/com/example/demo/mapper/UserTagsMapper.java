package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.UserTags;

/*
 * ユーザータグ登録用マッパー
 *作成者　奥野 
 */

@Mapper
public interface UserTagsMapper {
	// タグの登録
	 @Insert({
	        "<script>",
	        "INSERT INTO user_tags (user_id, tag_id) VALUES",
	        "<foreach collection='userTags' item='ut' separator=','>",
	        "(#{ut.user_id}, #{ut.tag_id})",
	        "</foreach>",
	        "</script>"
	    })
	    void insertUserTags(@Param("userTags") List<UserTags> userTags);
	 
	 
	 //タグの削除
	 @Delete("DELETE FROM user_tags WHERE user_id =#{user_id}")
	 void deleteByUserId(Integer userId);


}
