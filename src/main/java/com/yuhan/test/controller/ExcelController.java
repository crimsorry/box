package com.yuhan.test.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yuhan.test.dto.CompanyDTO;
import com.yuhan.test.dto.PagingDTO;
import com.yuhan.test.service.CompanyService;

@Controller
public class ExcelController {
	
	@Autowired
	private CompanyService service;
	
	// 엑셀 양식 다운
	@RequestMapping(value = "downloadExcelSampleFile", method = RequestMethod.POST)
    public String downloadExcelSampleFile(Model model) throws Exception {
		
        SXSSFWorkbook workbook = service.excelFileDownloadSampleProcess();
        
        model.addAttribute("locale", Locale.KOREA);
        model.addAttribute("workbook", workbook);
        model.addAttribute("workbookName", "거래처 샘플");
        
        return "excelDownloadView";
    }
	
	// 엑셀 다운
	@RequestMapping(value = "downloadExcelFile", method = RequestMethod.POST)
    public String downloadExcelFile(Model model) throws Exception {
		
        List<CompanyDTO> list = service.selectComList();
        
        SXSSFWorkbook workbook = service.excelFileDownloadProcess(list);
        
        model.addAttribute("locale", Locale.KOREA);
        model.addAttribute("workbook", workbook);
        model.addAttribute("workbookName", "거래처 관리");
        
        return "excelDownloadView";
    }
	
	// 엑셀 업로드
	@RequestMapping(value = "/uploadExcelFile", method = RequestMethod.POST)
	@ResponseBody // 데이터 넘겨줌
	public String uploadExcelFile(MultipartHttpServletRequest request, Model model, PagingDTO pdto, HttpSession session) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
		
		MultipartFile file = null;
        Iterator<String> iterator = request.getFileNames();
        if(iterator.hasNext()) {
            file = request.getFile(iterator.next());
        }
        System.out.println("file: " + file);
        String msg = service.uploadExcelFile(file, (String)session.getAttribute("company_name"));
        
        System.out.println("controller: " + msg);
        model.addAttribute("msg", msg); // 엑셀 업로드 실패/성공 메세지
        model.addAttribute("seemsg", 1); // 메세지 보여주기!
        
        msg = URLEncoder.encode(msg , "UTF-8");
        System.out.println("encode msg: " + msg);
        
        return msg;
        
    }

	
}
