package com.yuhan.test.service.mapper;

import java.util.List;

import com.yuhan.test.dto.CompanyDTO;
import com.yuhan.test.dto.PagingDTO;

public interface CompanyMapper {

	// 전체 리스트
	public List<CompanyDTO> selectList(PagingDTO pdto) throws Exception;
	
	// 전체 리스트(페이징X)
	public List<CompanyDTO> selectComList() throws Exception;
	
	// 전체 리스트 갯수
	public int cntList() throws Exception;
	
	// 상세보기
	public CompanyDTO selectOne(String company_code) throws Exception;
	
	// 수정하기
	public void updateCom(CompanyDTO cdto) throws Exception;
	
	// 추가하기
	public void insertCom(CompanyDTO cdto) throws Exception;
	
	// company_code 중복체크
	public int multiChk(String company_code) throws Exception;
	
	// 삭제하기
	public void deleteCom(String company_code) throws Exception;
	
}
