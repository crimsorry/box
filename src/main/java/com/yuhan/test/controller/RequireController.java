package com.yuhan.test.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.yuhan.test.dto.CompanyDTO;
import com.yuhan.test.dto.OrderDTO;
import com.yuhan.test.dto.PagingDTO;
import com.yuhan.test.service.LoginService;
import com.yuhan.test.service.RequireService;
import com.yuhan.test.service.UserService;

@Controller
public class RequireController {

	@Autowired
	private RequireService service;
	@Autowired
	private LoginService lservice;
	@Autowired
	private UserService uservice;
	
	// 박스 재고 관리
	@RequestMapping(value = "boxManagement")
	public String boxManagement(Model model, PagingDTO pdto, HttpSession session) throws Exception{
		
		String num = pdto.getNum();
		
		if(num.equals("one")) {
			int first = 0; // 첫 시작 번호
			int cnt = 20; // 화면에 뿌려줄 갯수
			int totalbtn = service.noBoxCnt(); // 총 버튼 수
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
				if(Integer.parseInt(pdto.getBtn())==totalbtn && service.noBoxCnt()%20!=0) {
					cnt = service.noBoxCnt()%20;
				}
				// 현재 페이지
				currentbtn = Integer.parseInt(pdto.getBtn());
			}
			
			Map<String, Integer> p = new HashMap<String, Integer>();
			p.put("first", first);
			p.put("cnt", cnt);
			
			pdto.setP(p);
			List<OrderDTO> list = service.noBoxRe(pdto);
			model.addAttribute("boxList", list);
			model.addAttribute("totalbtn", totalbtn);
	        model.addAttribute("currentbtn", currentbtn);
		}else if(num.equals("two")) {
			List<OrderDTO> list = lservice.boxList();
			model.addAttribute("boxList", list);
		}else if(num.equals("twotwo")) {
			int first = 0; // 첫 시작 번호
			int cnt = 20; // 화면에 뿌려줄 갯수
			int totalbtn = service.inoutListCnt(pdto.getBox_code()); // 총 버튼 수
			int remainTotal = service.inoutListCnt(pdto.getBox_code()); // 계속 남아있는 총 버튼 수
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
				if(Integer.parseInt(pdto.getBtn())==totalbtn && remainTotal%20!=0) {
					cnt = remainTotal%20;
				}
				// 현재 페이지
				currentbtn = Integer.parseInt(pdto.getBtn());
			}
			
			Map<String, Integer> p = new HashMap<String, Integer>();
			p.put("first", first);
			p.put("cnt", cnt);
			
			pdto.setP(p);
			
			// 박스재고 리스트
			List<OrderDTO> lists = lservice.boxList();
			model.addAttribute("boxList", lists);
			Map<String, String> p2 = new HashMap<String, String>();
			p2.put("box_code", pdto.getBox_code());
			
			pdto.setP2(p2);
			
			// 상세재고 리스트
			List<OrderDTO> list = service.inoutList(pdto);
			model.addAttribute("boxtwoList", list);
			model.addAttribute("box_code", pdto.getBox_code());
			model.addAttribute("totalbtn", totalbtn);
	        model.addAttribute("currentbtn", currentbtn);
		}else if(num.equals("three")) {
			List<CompanyDTO> comList = service.comList();
			List<OrderDTO> list = lservice.boxList();
			model.addAttribute("boxList", list);
			model.addAttribute("comList", comList);
		}else if(num.equals("five")) {
			List<OrderDTO> list = lservice.boxList();
			model.addAttribute("boxList", list);
		}
		
		Date date = new Date();
    	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
    	String now = transFormat.format(date); 
    	
    	Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
		
		
        model.addAttribute("now", now);
        model.addAttribute("tomorrow", transFormat.format(cal.getTime()));
        model.addAttribute("num", num);
        
        model.addAttribute("company_name", (String)session.getAttribute("company_name"));
		return "management";
	}
	
	// 배송!
	@RequestMapping(value = "updateBox")
	public String updateBox(Model model, @RequestParam("order_code") String order_code, @RequestParam("num") String num) throws Exception{
		
		service.updateCnt(order_code);
		
		return "redirect:boxManagement?num=" + num;
	}
	
	// 날짜로 조회!
	@RequestMapping(value = "dateSee")
	public String dateSee(Model model, PagingDTO pdto, HttpSession session) throws Exception{
		Map<String, String> p2 = new HashMap<String, String>();
		p2.put("box_code", pdto.getBox_code());
		p2.put("startDate", pdto.getStartDate());
		p2.put("endDate", pdto.getEndDate());
		pdto.setP2(p2);
		
		int first = 0; // 첫 시작 번호
		int cnt = 20; // 화면에 뿌려줄 갯수
		int totalbtn = service.inoutListCnt2(pdto); // 총 버튼 수
		if(totalbtn==0) {
			List<OrderDTO> list = lservice.boxList();
			model.addAttribute("boxList", list);
			model.addAttribute("msg", "조회된 컬럼이 존재하지 않습니다!");
			model.addAttribute("see", 1);
			Date date = new Date();
	    	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	String now = transFormat.format(date); 
	    	
	    	Calendar cal = Calendar.getInstance();
	        cal.setTime(new Date());
	        cal.add(Calendar.DATE, 1);
			
	        model.addAttribute("now", now);
	        model.addAttribute("tomorrow", transFormat.format(cal.getTime()));
	        model.addAttribute("num", "two");
			return "management";
		}else {
			int remainTotal = service.inoutListCnt2(pdto); // 계속 남아있는 총 버튼 수
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
				if(Integer.parseInt(pdto.getBtn())==totalbtn && remainTotal%20!=0) {
					cnt = remainTotal%20;
				}
				// 현재 페이지
				currentbtn = Integer.parseInt(pdto.getBtn());
			}
			
			Map<String, Integer> p = new HashMap<String, Integer>();
			p.put("first", first);
			p.put("cnt", cnt);
			
			pdto.setP(p);
			
			// 박스재고 리스트
			List<OrderDTO> lists = lservice.boxList();
			model.addAttribute("boxList", lists);
			
			// 상세재고 리스트
			List<OrderDTO> list = service.inoutList2(pdto);
			model.addAttribute("boxtwoList", list);
			model.addAttribute("box_code", pdto.getBox_code());
			model.addAttribute("totalbtn", totalbtn);
	        model.addAttribute("currentbtn", currentbtn);
	        model.addAttribute("num", "twotwo");
	        model.addAttribute("dateChk", 1);
	        model.addAttribute("startDate", pdto.getStartDate());
	        model.addAttribute("endDate", pdto.getEndDate());
	        model.addAttribute("company_name", (String)session.getAttribute("company_name"));
	        
			return "management";
		}
	}
	
	// 박스 구매(out)
	@RequestMapping(value = "boxOut")
	public String boxOut(Model model, OrderDTO odto, PagingDTO pdto, HttpSession session) throws Exception{
		
		Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyMMdd");
        
        // 주문번호 구하기
        String orderno = uservice.findOrderNum();
        if(orderno==null) {
        	orderno = "0";
        }
        
        // 자릿수 구하기
        int order_code = Integer.parseInt(orderno)+1;
        String blank = "";
        if((int)(Math.log10(order_code)+1)==3) {
        	blank = "0";
        }else if((int)(Math.log10(order_code)+1)==2) {
        	blank = "00";
        }else if((int)(Math.log10(order_code)+1)==1) {
        	blank = "000";
        }
        
        String code = df.format(cal.getTime()) + "P" + blank + order_code;
        odto.setOrder_code(code);
		
		uservice.ordered(odto);
		
		Map<String, Integer> p = new HashMap<String, Integer>();
		Map<String, String> p2 = new HashMap<String, String>();
		
		p.put("cnt", odto.getOrder_cnt());
		p2.put("code", odto.getBox_code());
		pdto.setP(p);
		pdto.setP2(p2);
		
		uservice.updateCnt(pdto);
		
		List<CompanyDTO> comList = service.comList();
		List<OrderDTO> list = lservice.boxList();
		
		Date date = new Date();
    	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
    	String now = transFormat.format(date); 
    	
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        
        model.addAttribute("tomorrow", transFormat.format(cal.getTime()));
        model.addAttribute("now", now);
        model.addAttribute("msg", "요청이 완료되었습니다.");
		model.addAttribute("see", 1);
		model.addAttribute("num", "three");
		model.addAttribute("boxList", list);
		model.addAttribute("comList", comList);
		model.addAttribute("company_name", (String)session.getAttribute("company_name"));
		
		return "management";
	}
	
	// 신규박스입력(In)
	@RequestMapping(value = "boxIn")
	public String boxIn(Model model, OrderDTO odto, HttpSession session) throws Exception{
		
		String boxType = odto.getBox_type();
		int boxNameChk = service.boxNameChk(boxType);
		
		if(boxNameChk>0) {
			model.addAttribute("msg", "중복된 박스명입니다!");
		}else {
			String boxCode = service.maxBox();
			System.out.println(boxCode);
			int num = Integer.parseInt(boxCode)+1;
			odto.setBox_code("B" + num);
			
			service.insertBox(odto);
			
			String orderno = uservice.findOrderNum();
	        if(orderno==null) {
	        	orderno = "0";
	        }
	        
	        // 자릿수 구하기
	        int order_code = Integer.parseInt(orderno)+1;
	        String blank = "";
	        if((int)(Math.log10(order_code)+1)==3) {
	        	blank = "0";
	        }else if((int)(Math.log10(order_code)+1)==2) {
	        	blank = "00";
	        }else if((int)(Math.log10(order_code)+1)==1) {
	        	blank = "000";
	        }
	        
	        Calendar cal = Calendar.getInstance();
	        DateFormat df = new SimpleDateFormat("yyMMdd");
	        String code = df.format(cal.getTime()) + "P" + blank + order_code;
	        System.out.println("code: " + code);
	        odto.setOrder_code(code);
	        
			uservice.orderIn(odto);
			
			model.addAttribute("msg", "요청이 완료되었습니다!");
		}
		
		model.addAttribute("see", 1);
		model.addAttribute("num", "four");
		model.addAttribute("company_name", (String)session.getAttribute("company_name"));
		
		return "management";
	}
	
	// 박스 입고(in)
	@RequestMapping(value = "oldBoxIn")
	public String oldBoxIn(Model model, OrderDTO odto, PagingDTO pdto, HttpSession session) throws Exception{
		
		Map<String, Integer> p = new HashMap<String, Integer>();
		Map<String, String> p2 = new HashMap<String, String>();
		
		p.put("cnt", odto.getBox_cnt());
		p2.put("box_code", odto.getBox_code());
		pdto.setP(p);
		pdto.setP2(p2);
		
		// 박스 입고
		service.oldBoxIn(pdto);
		
		String orderno = uservice.findOrderNum();
        if(orderno==null) {
        	orderno = "0";
        }
        
        // 자릿수 구하기
        int order_code = Integer.parseInt(orderno)+1;
        String blank = "";
        if((int)(Math.log10(order_code)+1)==3) {
        	blank = "0";
        }else if((int)(Math.log10(order_code)+1)==2) {
        	blank = "00";
        }else if((int)(Math.log10(order_code)+1)==1) {
        	blank = "000";
        }
        
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyMMdd");
        String code = df.format(cal.getTime()) + "P" + blank + order_code;
        odto.setOrder_code(code);
        
        // 박스 입고 내역
		uservice.orderIn(odto);
		
		List<OrderDTO> list = lservice.boxList();
		model.addAttribute("boxList", list);
		model.addAttribute("msg", "요청이 완료되었습니다!");
		model.addAttribute("see", 1);
		model.addAttribute("num", "five");
		model.addAttribute("company_name", (String)session.getAttribute("company_name"));
		
		return "management";
	}
	
	
	
	
}


























