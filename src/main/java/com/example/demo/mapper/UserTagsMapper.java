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
	@Delete({
		"<script>",
        "DELETE FROM user_tags WHERE user_id = #{userId} AND tag_id IN",
        "<foreach collection='tagsToRemove' item='tag' open='(' separator=',' close=')'>",
        "#{tag}",
        "</foreach>",
        "</script>"
    	})
		void deleteTags(@Param("userId") Integer userId, @Param("tagsToRemove") List<Integer> tagsToRemove);

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

}
