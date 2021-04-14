package com.yuhan.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuhan.test.dto.BoardDTO;
import com.yuhan.test.dto.CompanyDTO;
import com.yuhan.test.dto.PagingDTO;
import com.yuhan.test.service.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper mapper;
	
	// 전체 리스트
	public List<BoardDTO> boardList(PagingDTO pdto) throws Exception {
		return mapper.boardList(pdto);
	}
	
	// 전체 리스트 갯수
	public int cntList() throws Exception {
		return mapper.cntList();
	}
	
	// 상세보기
	public BoardDTO boardDetail(int board_code) throws Exception {
		return mapper.boardDetail(board_code);
	}
	
	// 상세보기 클릭 시 조회수 증가
	public void updateCnt(int board_code) throws Exception {
		mapper.updateCnt(board_code);
	}
	
	// 추가하기
	public void boardWrite(BoardDTO bdto) throws Exception {
		mapper.boardWrite(bdto);
	}
	
	// 전체댓글
	public List<BoardDTO> commentList(PagingDTO pdto) throws Exception{
		return mapper.commentList(pdto);
	}
	
	// 전체 댓글 수
	public int commentCnt(int board_code) throws Exception {
		return mapper.commentCnt(board_code);
	}
	
	// max re_group
	public int maxGroup(int board_code) throws Exception {
		return mapper.maxGroup(board_code);
	}
	
	// max re_group
	public int maxLevel(int board_code) throws Exception {
		return mapper.maxLevel(board_code);
	}
	
	// max re_group
	public int maxFirstLevel(BoardDTO bdto) throws Exception {
		return mapper.maxFirstLevel(bdto);
	}
	
	// 대댓글 level+1
	public void rerePlus(BoardDTO bdto) throws Exception {
		mapper.rerePlus(bdto);
	}
	
	// 댓글 작성
	public void writeComment(BoardDTO bdto) throws Exception {
		mapper.writeComment(bdto);
	}
	
	// 댓글 수정
	public void rewriteComment(BoardDTO bdto) throws Exception {
		mapper.rewriteComment(bdto);
	}
	
	// 댓글 삭제
	public void commentChk(PagingDTO pdto) throws Exception{
		mapper.commentChk(pdto);
	}
	
	// max re_group
	public int rereChk(int re_group) throws Exception {
		return mapper.rereChk(re_group);
	}
	
	// 댓글 작성
	public void rerewrite(BoardDTO bdto) throws Exception {
		mapper.rerewrite(bdto);
	}
	
	// 상세보기 클릭 시 조회수 증가
	public void updateBoard(BoardDTO bdto) throws Exception {
		mapper.updateBoard(bdto);
	}
	
	// 삭제하기
	public void deleteWrite(int board_code) throws Exception{
		mapper.deleteWrite(board_code);
	}
	
}




















