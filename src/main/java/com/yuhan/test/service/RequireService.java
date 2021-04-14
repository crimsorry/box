package com.yuhan.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuhan.test.dto.BoardDTO;
import com.yuhan.test.dto.CompanyDTO;
import com.yuhan.test.dto.OrderDTO;
import com.yuhan.test.dto.PagingDTO;
import com.yuhan.test.service.mapper.RequireMapper;

@Service
public class RequireService {

	@Autowired
	private RequireMapper mapper;
	
	// 미완료건 조회
	public List<OrderDTO> noBoxRe(PagingDTO pdto) throws Exception {
		return mapper.noBoxRe(pdto);
	}
	
	//미완료건 갯수 
	public int noBoxCnt() throws Exception{
		return mapper.noBoxCnt();
	}
	
	// 발송하기
	public void updateCnt(String order_code) throws Exception{
		mapper.updateCnt(order_code);
	}
	
	// 박스 in/out 리스트
	public List<OrderDTO> inoutList(PagingDTO pdto) throws Exception {
		return mapper.inoutList(pdto);
	}
	
	// 박스 in/out 리스트
	public List<OrderDTO> inoutList2(PagingDTO pdto) throws Exception {
		return mapper.inoutList2(pdto);
	}
	
	// 박스 in/out 리스트 갯수
	public int inoutListCnt(String box_code) throws Exception{
		return mapper.inoutListCnt(box_code);
	}
	
	// 박스 in/out 리스트 갯수2
	public int inoutListCnt2(PagingDTO pdto) throws Exception{
		return mapper.inoutListCnt2( pdto);
	}
	
	// 거래처 리스트
	public List<CompanyDTO> comList() throws Exception {
		return mapper.comList();
	}
	
	// 박스 insert
	public void insertBox(OrderDTO odto) throws Exception {
		mapper.insertBox(odto);
	}
		
	// 박스명 중복체크 
	public int boxNameChk(String box_type) throws Exception {
		return mapper.boxNameChk(box_type);
	}
	
	// max 박스 코드 
	public String maxBox() throws Exception{
		return mapper.maxBox();
	}
	
	// 박스 입고
	public void oldBoxIn(PagingDTO pdto) throws Exception{
		mapper.oldBoxIn(pdto);
	}
	
}





















