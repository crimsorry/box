package com.yuhan.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuhan.test.dto.PagingDTO;

@Controller
public class HomeController {
	
    @RequestMapping(value = "/")
    public String home(Model model, PagingDTO pdto) throws Exception {
    	
    	return "login";
    }
	
}


















