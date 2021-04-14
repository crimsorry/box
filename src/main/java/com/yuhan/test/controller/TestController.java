package com.yuhan.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuhan.test.service.TestService;

@Controller
public class TestController {

	@Autowired
	private TestService service;
	
	@RequestMapping("/selectNow.do")
	public String selectNow() {
		String result = service.selectNow();
		System.out.println(result);
		return "index";
	}
	
}
