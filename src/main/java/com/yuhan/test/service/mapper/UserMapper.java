package com.yuhan.test.service.mapper;

import com.yuhan.test.dto.OrderDTO;
import com.yuhan.test.dto.PagingDTO;


public interface UserMapper {

	// 박스 요청
	public void ordered(OrderDTO odto) throws Exception;
	
	// 박스 요청(in)
	public void orderIn(OrderDTO odto) throws Exception;
	
	// 주문번호 찾기!
	public String findOrderNum() throws Exception;
	
	// 재고수 감소
	public void updateCnt(PagingDTO pdto) throws Exception;
	
}
