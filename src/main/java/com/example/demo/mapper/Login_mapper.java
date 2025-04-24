package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.LoginEntity;

@Mapper
public interface Login_mapper {

	@Select("SELECT * FROM users WHERE user_name = #{user_name} AND user_pass = #{user_pass}")
    LoginEntity findByLogin(@Param("user_name") String user_name, @Param("user_pass") String user_pass);

}
