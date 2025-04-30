package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.LoginEntity;

/*
 * DBアクセスインターフェース 
 * 作成者 北川
 */

@Mapper
public interface Login_mapper {

	//ここでデータベースからユーザー名が一致するやつを探す。
	//パスワードはハッシュ値との比較ができ名から一旦スルー。　
	@Select("SELECT * FROM users WHERE user_name = #{user_name}")
    LoginEntity findByName(@Param("user_name") String user_name);

}
