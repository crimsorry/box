package com.yuhan.test.controller;

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

import com.yuhan.test.dto.OrderDTO;
import com.yuhan.test.dto.PagingDTO;
import com.yuhan.test.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService service;
	
	// 로그인 체크
	@RequestMapping(value = "loginChk")
	public String loginChk(Model model, @RequestParam("id") String id, @RequestParam("password") String password, PagingDTO pdto, HttpSession session) throws Exception{
		
		Map<String, String> p2 = new HashMap<String, String>();
		
		p2.put("id", id);
		p2.put("password", password);
		pdto.setP2(p2);
		
		int idChk = service.IDChk(id); // 사용자 ID 확인(1)
		int pwChk = service.PWChk(pdto); // 사용자 ID 확인(1)
		
		if(idChk!=1) {
			model.addAttribute("msg", "올바르지 않은 ID입니다. 다시 확인해주세요.");
			return "sucessChk";
		}else if(pwChk<1) {
			model.addAttribute("msg", "올바르지 않은 PW입니다. 다시 확인해주세요.");
			return "sucessChk";
		}else {
			if(id.equals("admin") && password.equals("admin1") || id.equals("admin1") && password.equals("admin1")) {
				session.setAttribute("company_code", id);
				String company_name = service.ComName(id);
				session.setAttribute("company_name", company_name);
				return "redirect:main";
			}else {
				Date date = new Date();
	        	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
	        	String now = transFormat.format(date);    
				
	        	Calendar cal = Calendar.getInstance();
	        	cal.setTime(new Date());
	            cal.add(Calendar.DATE, 1);
	            
				List<OrderDTO> list = service.boxList();
				model.addAttribute("boxList", list);
				String company_name = service.ComName(id);
				session.setAttribute("company_code", id);
				session.setAttribute("company_name", company_name);
				model.addAttribute("company_name", (String)session.getAttribute("company_name"));
				model.addAttribute("now", now);
				model.addAttribute("tomorrow", transFormat.format(cal.getTime()));
				return "userBox";
			}
		}
	}
	
	// userbox로 가지
	@RequestMapping(value = "goUserBox")
	public String goUserBox(Model model, HttpSession session) throws Exception{
		
		String company_name = (String)session.getAttribute("company_name");
		
		Date date = new Date();
    	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
    	String now = transFormat.format(date);    
		
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        
		List<OrderDTO> list = service.boxList();
		model.addAttribute("boxList", list);
		model.addAttribute("company_name", company_name);
		model.addAttribute("now", now);
		 model.addAttribute("tomorrow", transFormat.format(cal.getTime()));
		return "userBox";
		
	}
	
	// 로그아웃
	@RequestMapping(value = "logout")
	public String logout(Model model, HttpSession session) throws Exception{
		
		session.invalidate();
		
		return "redirect:/";
		
	}
	
}

























