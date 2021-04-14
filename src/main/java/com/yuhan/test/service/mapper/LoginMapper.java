package com.yuhan.test.service.mapper;

import java.util.List;

import com.yuhan.test.dto.BoardDTO;
import com.yuhan.test.dto.OrderDTO;
import com.yuhan.test.dto.PagingDTO;

public interface LoginMapper {
	
	// 사용자 ID 확인
	public int IDChk(String company_code) throws Exception;
		
	// 사용자 pw 확인
	public int PWChk(PagingDTO pdto) throws Exception;
	
	// 박스 리스트
	public List<OrderDTO> boxList() throws Exception;
	
	// 회사 이름 
	public String ComName(String company_code) throws Exception;
	
}
