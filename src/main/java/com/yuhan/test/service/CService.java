package com.yuhan.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuhan.test.domain.CompanyVO;

@Service
public class CService {
	
	@Autowired
    private CMapper mapper;

	public List<CompanyVO> selectList() {
		// TODO Auto-generated method stub
		return mapper.selectList();
	}
	
}
