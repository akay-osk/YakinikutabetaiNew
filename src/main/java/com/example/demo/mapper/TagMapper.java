package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/*
 * タグ用マッパー
 * 作成者　奥野
 */
@Mapper
public interface TagMapper {
	List<String>findNamesByIds(@Param("tagIds")List<Integer>tagIds);
	

}
