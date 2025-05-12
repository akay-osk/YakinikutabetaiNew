package com.example.demo.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Matching;

/*
 * マッチング用マッパー
 * 作成者　奥野
 */
@Mapper
public interface MatchingMapper {
	
	//マッチング希望条件入力
	@Insert("INSERT INTO matching (user_id,matching_day,matching_time,matching_gender,matching_min_age,matching_max_age,matching_member,matching_area) VALUES(#{user_id},#{matching_day},#{matching_time},#{matching_gender},#{matching_min_age},#{matching_max_age},#{matching_member},#{matching_area})")
	void insert(Matching matching);
	
	
	//マッチング検索
	@Select("SELECT * ,((CASE WHEN matching_gender = #{matching_gender} THEN 5 ELSE 0 END) +(CASE WHEN matching_time = #{matching_time} THEN 4 ELSE 0 END) +(CASE WHEN matching_member = #{matching_member} THEN 3 ELSE 0) + (CASE WHEN ABS((#{minAge} + #{maxAge}) /2 - (matching_min_age + matching_max_age) /2 ) <= 5 THEN 3 ELSE 0 END) + (CASE WHEN matching_area = #{matching_area} THEN 2 WHEN matching_area = 'どこでもよい' OR #{matching_area} = 'どこでもよい' THEN 1 ELSE 0 END)) AS matching_score FROM matching WHERE user_id != #{user_id} AND user_id NOT IN (SELECT blocking_user_id FROM blocking WHERE user_id = #{user_id}) AND matching_day = #{matching_day} AND matching_max_age >= #{minAge} AND matching_min_age <= #{maxAge} AND matching_member = #{matching_member} AND (matching_area = #{matching_area} OR matching_area = 'どこでもよい' OR #{matching_area} = 'どこでもよい') ORDER BY matching_score DESC  LIMIT 3")
	List<Matching> findMatching(
			@Param("matching_day") LocalDate matching_day,
			@Param("matching_time") String matching_time,
			@Param("matching_gender") boolean matching_gender,
			@Param("minAge") int minAge,
			@Param("maxAge") int maxAge,
			@Param("matching_member") boolean matching_member,
			@Param("matching_area") String matching_area
			);
	
	@Select("SELECT * FROM matching WHERE user_id = #{userId} ORDER BY matching_id DESC LIMIT 1")
	Matching selectByUserId(@Param("userId") int userId);
	
	@Delete("DELETE * FROM mathing WHERE matching_id =#{mayching_id}")
	void delete(Matching matching);
	
}
