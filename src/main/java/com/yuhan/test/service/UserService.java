package com.yuhan.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuhan.test.dto.OrderDTO;
import com.yuhan.test.dto.PagingDTO;
import com.yuhan.test.service.mapper.UserMapper;

@Service
public class UserService {
	
	@Autowired
	private UserMapper mapper;

	// 박스 요청
	public void ordered(OrderDTO odto) throws Exception {
		mapper.ordered(odto);
	}
	
	// 박스 요청(in)
	public void orderIn(OrderDTO odto) throws Exception {
		mapper.orderIn(odto);
	}
	
	// 주문번호 찾기!
	public String findOrderNum() throws Exception {
		return mapper.findOrderNum();
	}
	
	// 재고수 감소
	public void updateCnt(PagingDTO pdto) throws Exception {
		mapper.updateCnt(pdto);
	}
	
}
