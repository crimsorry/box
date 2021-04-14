package com.yuhan.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuhan.test.dto.CompanyDTO;
import com.yuhan.test.dto.PagingDTO;
import com.yuhan.test.service.CompanyService;

@Controller
public class HomeController {
	
    @RequestMapping(value = "/")
    public String home(Model model, PagingDTO pdto) throws Exception {
    	
    	return "login";
    }
	
}


















