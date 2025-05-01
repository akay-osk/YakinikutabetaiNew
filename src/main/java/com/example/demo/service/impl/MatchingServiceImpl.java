package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Matching;
import com.example.demo.mapper.MatchingMapper;
import com.example.demo.service.MatchingService;

import lombok.RequiredArgsConstructor;
@Service
@Transactional
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

	@Autowired
	private MatchingMapper matchingMapper;
	
	@Override
	public void insertMatching(Matching matching) {
		matchingMapper.insert(matching);
	}

	@Override
	public List<Matching> findMatching(
			int user_id,
			LocalDate matching_day, 
			String matching_time,
			boolean matching_gender, 
			int minAge, 
			int maxAge, 
			boolean matching_member,
			String matching_area) {
		
			return matchingMapper.findMatching(
					user_id,
					matching_day,
					matching_time,
					matching_gender,
					minAge,
					maxAge,
					matching_member,
					matching_area
					);
	}

}
