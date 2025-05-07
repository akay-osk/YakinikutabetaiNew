package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Block;
import com.example.demo.mapper.BlockMapper;
import com.example.demo.service.BlockService;

@Service
@Transactional
public class BlockServiceImpl implements BlockService {
	
	@Autowired
	private BlockMapper blockMapper;

	@Override
	public void blockUser(Block block) {
		blockMapper.insert(block);
	

	}

}
