package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.UserTags;

/*
 * ユーザータグ登録用マッパー
 *作成者　奥野 
 */

@Mapper
public interface UserTagsMapper {
	
	//ユーザーの現在のタグ情報を取得
	@Select("SELECT tag_id FROM user_tags WHERE user_id = #{user_id}")
	List<Integer> selectTagsByUserId(Integer user_id);
	
	//指定されたタグを削除
	@Delete("DELETE FROM user_tags WHERE user_id = #{userId}")
	void deleteAllTagsByUserId(@Param("userId") Integer userId);
	
	// 新しいタグの登録
	@Insert({
		"<script>",
        "INSERT INTO user_tags (user_id, tag_id) VALUES ",
        "<foreach collection='userTags' item='ut' separator=','>",
        "(#{ut.user_id}, #{ut.tag_id})",
        "</foreach>",
        "</script>"
		})
		void insertUserTags(@Param("userTags") List<UserTags> userTags);

	@Select("SELECT t.tag_name FROM user_tags ut JOIN tags t ON ut.tag_id = t.tag_id WHERE ut.user_id =#{userId}")
	List<String> findTagNamesByUserId(@Param("userId") int userId);

}
