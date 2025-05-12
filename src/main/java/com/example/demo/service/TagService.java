package com.example.demo.service;

import java.util.List;

public interface TagService {
	
	 List<String> findTagNameByIds(List<Integer> tagIds);
		
	}

