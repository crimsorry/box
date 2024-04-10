package com.yuhan.test.service.mapper;

import java.util.List;

import com.yuhan.test.dto.CompanyDTO;
import com.yuhan.test.dto.OrderDTO;
import com.yuhan.test.dto.PagingDTO;

public interface RequireMapper {
		
	// 미완료건 조회
	public List<OrderDTO> noBoxRe(PagingDTO pdto) throws Exception;

	// 전체 리스트 갯수
	public int noBoxCnt() throws Exception;
	
	// 발송하기
	public void updateCnt(String order_code) throws Exception;
	
	// 박스 in/out 리스트
	public List<OrderDTO> inoutList(PagingDTO pdto) throws Exception;
	
	// 박스 in/out 리스트
	public List<OrderDTO> inoutList2(PagingDTO pdto) throws Exception;
	
	// 박스 in/out 리스트 갯수
	public int inoutListCnt(String box_code) throws Exception;
	
	// 박스 in/out 리스트 갯수2
	public int inoutListCnt2(PagingDTO pdto) throws Exception;
	
	// 거래처 리스트
	public List<CompanyDTO> comList() throws Exception;
	
	// 박스 insert
	public void insertBox(OrderDTO odto) throws Exception;
		
	// 박스명 중복체크 
	public int boxNameChk(String box_type) throws Exception;
		
	// max 박스 코드
	public String maxBox() throws Exception;
	
	// 박스 입고
	public void oldBoxIn(PagingDTO pdto) throws Exception;
	
	
}



























