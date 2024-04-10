package com.yuhan.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuhan.test.dto.OrderDTO;
import com.yuhan.test.dto.PagingDTO;
import com.yuhan.test.service.mapper.LoginMapper;

@Service
public class LoginService {

	@Autowired
	private LoginMapper mapper;
	
	// 사용자 ID 확인
	public int IDChk(String company_code) throws Exception {
		return mapper.IDChk(company_code);
	}
		
	// 사용자 pw 확인
	public int PWChk(PagingDTO pdto) throws Exception {
		return mapper.PWChk(pdto);
	}
	
	// 박스 리스트
	public List<OrderDTO> boxList() throws Exception {
		return mapper.boxList();
	}
	
	// 회사 이름 
	public String ComName(String company_code) throws Exception {
		return mapper.ComName(company_code);
	}
	
}
