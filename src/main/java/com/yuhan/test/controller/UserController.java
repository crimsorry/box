package com.yuhan.test.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuhan.test.dto.OrderDTO;
import com.yuhan.test.dto.PagingDTO;
import com.yuhan.test.service.LoginService;
import com.yuhan.test.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	private LoginService cservice;
	
	// 박스 요청
	@RequestMapping(value = "ordered")
	public String ordered(Model model, OrderDTO odto, PagingDTO pdto, HttpSession session) throws Exception{
		
		String no = (String)session.getAttribute("company_code");
		
		Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyMMdd");
        
        String orderno = service.findOrderNum();
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
        odto.setCompany_code(no);
		
		service.ordered(odto);
		
		Map<String, Integer> p = new HashMap<String, Integer>();
		Map<String, String> p2 = new HashMap<String, String>();
		
		p.put("cnt", odto.getOrder_cnt());
		p2.put("code", odto.getBox_code());
		pdto.setP(p);
		pdto.setP2(p2);
		
		service.updateCnt(pdto);
		model.addAttribute("msg", "요청이 완료되었습니다.");
		model.addAttribute("see", 1);
		model.addAttribute("company_name", (String)session.getAttribute("company_name"));
		List<OrderDTO> list = cservice.boxList();
		model.addAttribute("boxList", list);
		return "userBox";
	}
	
}
