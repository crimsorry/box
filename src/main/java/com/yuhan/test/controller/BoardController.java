package com.yuhan.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuhan.test.dto.BoardDTO;
import com.yuhan.test.dto.PagingDTO;
import com.yuhan.test.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService service;
	
	// 리스트보기
	@RequestMapping(value = "boardList")
	public String boardList(Model model, PagingDTO pdto, HttpSession session) throws Exception{
		
		int first = 0; // 첫 시작 번호
		int cnt = 20; // 화면에 뿌려줄 갯수
		int totalbtn = service.cntList(); // 총 버튼 수
		int currentbtn = 1; // 현재 누른 번호
		
		if(totalbtn%cnt!=0) {
			totalbtn = (totalbtn/cnt) + 1;
		}else {
			totalbtn = totalbtn/cnt;
		}
		
		// 2페이지 이상을 클릭했을때
		if(pdto.getBtn()!="" && pdto.getBtn()!=null && Integer.parseInt(pdto.getBtn())!=1) {
			first = (Integer.parseInt(pdto.getBtn())-1)*cnt;
			// 마지막 페이지
			if(Integer.parseInt(pdto.getBtn())==totalbtn && service.cntList()%20!=0) {
				cnt = service.cntList()%20;
			}
			// 현재 페이지
			currentbtn = Integer.parseInt(pdto.getBtn());
		}
		
		Map<String, Integer> p = new HashMap<String, Integer>();
		p.put("first", first);
		p.put("cnt", cnt);
		
		pdto.setP(p);
		
		List<BoardDTO> list = service.boardList(pdto);
		model.addAttribute("boardList", list);
		model.addAttribute("totalbtn", totalbtn);
        model.addAttribute("currentbtn", currentbtn);
        model.addAttribute("company_name", (String)session.getAttribute("company_name"));
		
		return "board";
	}
	
	// 상세보기
	@RequestMapping(value = "boardDetail")
	public String boardDetail(@RequestParam("board_code") String board_code, Model model, PagingDTO pdto) throws Exception{
		
		int code = Integer.parseInt(board_code);
		
		service.updateCnt(code); // 조회수 증가
		
		BoardDTO bdto = service.boardDetail(code); // 상세 게시글
		
		// 댓글 페이징
		int first = 0; // 첫 시작 번호
		int cnt = 20; // 화면에 뿌려줄 갯수
		int totalbtn = service.commentCnt(code); // 총 버튼 수
		int commentCnt = service.commentCnt(code); // 총 코멘트 수
		int currentbtn = 1; // 현재 누른 번호
		
		if(totalbtn%cnt!=0) {
			totalbtn = (totalbtn/cnt) + 1;
		}else {
			totalbtn = totalbtn/cnt;
		}
		
		// 2페이지 이상을 클릭했을때
		if(pdto.getBtn()!="" && pdto.getBtn()!=null && Integer.parseInt(pdto.getBtn())!=1) {
			first = (Integer.parseInt(pdto.getBtn())-1)*cnt;
			// 마지막 페이지
			if(Integer.parseInt(pdto.getBtn())==totalbtn && commentCnt%20!=0) {
				cnt = commentCnt%20;
			}
			// 현재 페이지
			currentbtn = Integer.parseInt(pdto.getBtn());
		}
		
		Map<String, Integer> p = new HashMap<String, Integer>();
		p.put("code", code);
		p.put("first", first);
		p.put("cnt", cnt);
		
		pdto.setP(p);
		
		List<BoardDTO> list = service.commentList(pdto); // 댓글 총 리스트
		
		model.addAttribute("boardDetail", bdto);
		model.addAttribute("commentDetail", list);
        model.addAttribute("currentbtn", currentbtn);
        model.addAttribute("totalbtn", totalbtn);
        model.addAttribute("commentCnt", commentCnt);
        model.addAttribute("board_code", code);
		
		return "boardDetail";
	}
	
	// 글쓰기
	@RequestMapping(value = "boardWrite")
	public String boardWrite(Model model, BoardDTO bdto) throws Exception{
		
		service.boardWrite(bdto);
		
		return "redirect:boardList";
	}
	
	// 댓글 등록
	@RequestMapping(value = "writeComment")
	public String writeComment(Model model, BoardDTO bdto) throws Exception{
		
		int max = service.maxGroup(bdto.getBoard_code());
		bdto.setCnt(max);
		service.writeComment(bdto);
		
		return "redirect:boardDetail?board_code=" + bdto.getBoard_code();
	}
	
	// 댓글 삭제
	@RequestMapping(value = "deleteComment")
	public String deleteComment(Model model, BoardDTO bdto, PagingDTO pdto) throws Exception{
		
		int chk = service.rereChk(bdto.getRe_group());
		
		if(chk>1) {
			Map<String, Integer> p = new HashMap<String, Integer>();
			p.put("num", 2);
			p.put("comment_code", bdto.getComment_code());
			
			pdto.setP(p);
			service.commentChk(pdto);
		}else {
			Map<String, Integer> p = new HashMap<String, Integer>();
			p.put("num", 1);
			p.put("comment_code", bdto.getComment_code());
			
			pdto.setP(p);
			service.commentChk(pdto);
		}
		
		return "redirect:boardDetail?board_code=" + bdto.getBoard_code();
	}
	
	// 댓글 수정
	@RequestMapping(value = "rewriteComment")
	public String rewriteComment(Model model, BoardDTO bdto) throws Exception{
		
		service.rewriteComment(bdto);
		
		return "redirect:boardDetail?board_code=" + bdto.getBoard_code();
	}
	
	// 대댓글 달기
	@RequestMapping(value = "rereComment")
	public String rereComment(Model model, BoardDTO bdto) throws Exception{
		
		int maxFirstLevel = service.maxFirstLevel(bdto);
		
		bdto.setRe_level(maxFirstLevel);
		service.rerePlus(bdto);
		service.rerewrite(bdto);
		
		return "redirect:boardDetail?board_code=" + bdto.getBoard_code();
	}
	
	// 게시글 수정 페이지
	@RequestMapping(value = "boardFix")
	public String boardFix(@RequestParam("board_code") String board_code, Model model) throws Exception{
		
		int code = Integer.parseInt(board_code);
		
		BoardDTO bdto = service.boardDetail(code); // 상세 게시글
		
		model.addAttribute("boardDetail", bdto);
		model.addAttribute("board_code", code);
		
		return "boardFix";
	}
	
	// 게시글 수정 페이지
	@RequestMapping(value = "rewrite")
	public String rewrite(@RequestParam("board_code") String board_code, BoardDTO bdto, Model model) throws Exception{
		
		int code = Integer.parseInt(board_code);
		
		bdto.setBoard_code(code);
		
		service.updateBoard(bdto); // 게시글 수정
		
		return "redirect:boardDetail?board_code=" + bdto.getBoard_code();
	}
	
	// 게시글 삭제
	@RequestMapping(value = "deleteWrite")
	public String deleteWrite(@RequestParam("board_code") String board_code, Model model) throws Exception{
		
		int code = Integer.parseInt(board_code);
		
		service.deleteWrite(code);
		
		return "redirect:boardList";
	}
	
	
}


























