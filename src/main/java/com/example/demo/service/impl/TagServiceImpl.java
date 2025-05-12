package com.example.demo.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.TagMapper;
import com.example.demo.service.TagService;

@Service
@Transactional
public class TagServiceImpl implements TagService{
	
	@Autowired
	private TagMapper tagMapper;
	
	public List<String> findTagNameByIds(List<Integer> tagIds){
		if(tagIds == null || tagIds.isEmpty()) {
			return Collections.emptyList();
		}
		return tagMapper.findNamesByIds(tagIds);
	}
	

	
	

}
