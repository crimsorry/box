package com.yuhan.test.service.mapper;

import java.util.List;

import com.yuhan.test.dto.BoardDTO;
import com.yuhan.test.dto.PagingDTO;

public interface BoardMapper {

	// 전체 리스트
	public List<BoardDTO> boardList(PagingDTO pdto) throws Exception;
	
	// 전체 리스트 갯수
	public int cntList() throws Exception;
	
	// 상세보기
	public BoardDTO boardDetail(int board_code) throws Exception;
	
	// 상세보기 클릭 시 조회수 증가
	public void updateCnt(int board_code) throws Exception;
	
	// 추가하기
	public void boardWrite(BoardDTO bdto) throws Exception;
	
	// 전체댓글
	public List<BoardDTO> commentList(PagingDTO pdto) throws Exception;
	
	// 전체 댓글 수
	public int commentCnt(int board_code) throws Exception;
	
	// max re_group
	public int maxGroup(int board_code) throws Exception;
	
	// max re_level
	public int maxLevel(int board_code) throws Exception;
	
	// 최초 첫번째 max re_level
	public int maxFirstLevel(BoardDTO bdto) throws Exception;
	
	// 대댓글 level+1
	public void rerePlus(BoardDTO bdto) throws Exception;
	
	// 댓글 작성
	public void writeComment(BoardDTO bdto) throws Exception;
	
	// 댓글 수정
	public void rewriteComment(BoardDTO bdto) throws Exception;
	
	// 댓글 삭제
	public void commentChk(PagingDTO pdto) throws Exception;
	
	// 대댓글 달렸는지 여부
	public int rereChk(int re_group) throws Exception;
	
	// 대댓글 작성
	public void rerewrite(BoardDTO bdto) throws Exception;
	
	// 게시글 수정하기
	public void updateBoard(BoardDTO bdto) throws Exception;
	
	// 게시글 삭제
	public void deleteWrite(int board_code) throws Exception;
	
}




























