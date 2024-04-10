package com.yuhan.test.controller;

import java.text.SimpleDateFormat;
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

import com.yuhan.test.dto.CompanyDTO;
import com.yuhan.test.dto.PagingDTO;
import com.yuhan.test.service.CompanyService;

import net.crizin.KoreanCharacter;
import net.crizin.KoreanRomanizer;

@Controller
public class CompanyController {

	@Autowired
	private CompanyService service;
	
	// 상세보기
	@RequestMapping(value = "detail")
    public String selectOne(Model model, @RequestParam("company_code") String c_code, PagingDTO pdto, HttpSession session) throws Exception {
		
		int first = 0;
		int cnt = 20;
		int totalbtn = service.cntList(); // 총 버튼 수
		int remaintotalbtn = service.cntList(); // 계속 남아있는 총 버튼 수
		int currentbtn = 1;
		
		if(totalbtn%cnt!=0) {
			totalbtn = (totalbtn/cnt) + 1;
		}else {
			totalbtn = totalbtn/cnt;
		}
		
		// 2페이지 이상을 클릭했을때
		if(pdto.getBtn()!="" && pdto.getBtn()!=null && Integer.parseInt(pdto.getBtn())!=1) {
			first = (Integer.parseInt(pdto.getBtn())-1)*cnt;
			// 마지막 페이지
			if(Integer.parseInt(pdto.getBtn())==totalbtn && remaintotalbtn%20!=0) {
				cnt = remaintotalbtn%20;
			}
			currentbtn = Integer.parseInt(pdto.getBtn());
		}
		
		
		Map<String, Integer> p = new HashMap<String, Integer>();
		p.put("first", first);
		p.put("cnt", cnt);
		
		pdto.setP(p);
    	
    	List<CompanyDTO> list = service.selectList(pdto); // list 변수에 결과 값을 담는다
        model.addAttribute("companyList", list);
        
    	CompanyDTO cdto = service.selectOne(c_code);
        model.addAttribute("detailList", cdto); // model에 데이터 값을 담는다
        model.addAttribute("see", 1); // 팝업 보이게
        model.addAttribute("totalbtn", totalbtn);
        model.addAttribute("currentbtn", currentbtn);
        model.addAttribute("remaintotalbtn", remaintotalbtn);
        model.addAttribute("company_name", (String)session.getAttribute("company_name"));
        
        return "index"; // index.jsp로 이동
    }
	
	// 주소 영문 변환 API
	@RequestMapping(value="engChange")
	public String engchange(@RequestParam("addressEng") String addressEng, Model model) {
		
		String addressEngs = KoreanRomanizer.romanize(addressEng,KoreanCharacter.Type.District); 
		
		model.addAttribute("english", addressEngs); 
		
		return "engChangeForm";
	}
	
	// 구글 Map API
	@RequestMapping(value="gogleMap")
	public String gogelmap(@RequestParam("lat") String lat, @RequestParam("lng") String lng, Model model) {
		double mylat = Double.parseDouble(lat);
		double mylng = Double.parseDouble(lng);
		model.addAttribute("lat", mylat); 
		model.addAttribute("lng", mylng); 
		
		return "gogelMapForm";
	}
    
	// 수정하기
	@RequestMapping(value="update")
	public String updateCon(Model model, CompanyDTO cdto, PagingDTO pdto, HttpSession session) throws Exception{
		if(cdto.getStart_date()=="") {
			cdto.setStart_date(null);
		}
		if(cdto.getEnd_date()=="") {
			cdto.setEnd_date(null);
		}
		if(cdto.getModify_date()=="") {
			cdto.setModify_date(null);
		}
		Date date = new Date();
    	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String now = transFormat.format(date); 
    	cdto.setModify_date(now);
    	cdto.setModifier((String)session.getAttribute("company_name"));
    	if(cdto.getLat()=="" && cdto.getLng()=="" || cdto.getLat().equals("0") && cdto.getLng().equals("0") || cdto.getLat()==null && cdto.getLng()==null) {
			cdto.setLat(null);
			cdto.setLng(null);
		}
		service.updateCom(cdto);
		int first = 0;
		int cnt = 20;
		int totalbtn = service.cntList(); // 총 버튼 수
		int remaintotalbtn = service.cntList(); // 계속 남아있는 총 버튼 수
		int currentbtn = 1;
		
		if(totalbtn%cnt!=0) {
			totalbtn = (totalbtn/cnt) + 1;
		}else {
			totalbtn = totalbtn/cnt;
		}
		
		// 2페이지 이상을 클릭했을때
		if(pdto.getBtn()!="" && pdto.getBtn()!=null && Integer.parseInt(pdto.getBtn())!=1) {
			first = (Integer.parseInt(pdto.getBtn())-1)*cnt;
			// 마지막 페이지
			if(Integer.parseInt(pdto.getBtn())==totalbtn && remaintotalbtn%cnt!=0) {
				cnt = remaintotalbtn%20;
			}
			currentbtn = Integer.parseInt(pdto.getBtn());
		}
		
		
		Map<String, Integer> p = new HashMap<String, Integer>();
		p.put("first", first);
		p.put("cnt", cnt);
		
		pdto.setP(p);
    	
    	List<CompanyDTO> list = service.selectList(pdto); // list 변수에 결과 값을 담는다
        model.addAttribute("companyList", list);
        
    	cdto = service.selectOne(cdto.getCompany_code());
        model.addAttribute("detailList", cdto); // model에 데이터 값을 담는다
        model.addAttribute("see", 1); // 팝업 보이게
        model.addAttribute("totalbtn", totalbtn);
        model.addAttribute("currentbtn", currentbtn);
        model.addAttribute("remaintotalbtn", remaintotalbtn);
        model.addAttribute("seeMSG", 1); // 팝업 보이게
        model.addAttribute("msg", "수정이 완료되었습니다.");
        model.addAttribute("company_name", (String)session.getAttribute("company_name"));
        
        return "index"; // index.jsp로 이동
	}
	
	// 추가하기
	@RequestMapping(value="insert")
	public String insertCom(Model model, CompanyDTO cdto, PagingDTO pdto, HttpSession session) throws Exception{
		if(service.multiChk(cdto.getCompany_code())==1) {
			model.addAttribute("msg", "거래처가 중복되면 안됩니다.");
		}else {
			if(cdto.getStart_date()=="") {
				cdto.setStart_date(null);
			}
			if(cdto.getEnd_date()=="") {
				cdto.setEnd_date(null);
			}
			if(cdto.getModify_date()=="") {
				cdto.setModify_date(null);
			}
			if(cdto.getLat()=="" && cdto.getLng()=="" || cdto.getLat().equals("0") && cdto.getLng().equals("0") || cdto.getLat()==null && cdto.getLng()==null) {
				cdto.setLat(null);
				cdto.setLng(null);
			}
			Date date = new Date();
	    	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	String now = transFormat.format(date); 
			cdto.setInput_date(now);
			cdto.setInpuner((String)session.getAttribute("company_name"));
			service.insertCom(cdto);
			model.addAttribute("msg", "추가 완료되었습니다.");
		}
		int first = 0;
		int cnt = 20;
		int totalbtn = service.cntList(); // 총 버튼 수
		int remaintotalbtn = service.cntList(); // 계속 남아있는 총 버튼 수
		int currentbtn = 1;
		
		if(totalbtn%cnt!=0) {
			totalbtn = (totalbtn/cnt) + 1;
		}else {
			totalbtn = totalbtn/cnt;
		}
		
		// 2페이지 이상을 클릭했을때
		if(pdto.getBtn()!="" && pdto.getBtn()!=null && Integer.parseInt(pdto.getBtn())!=1) {
			first = (Integer.parseInt(pdto.getBtn())-1)*cnt;
			// 마지막 페이지
			if(Integer.parseInt(pdto.getBtn())==totalbtn && remaintotalbtn%cnt!=0) {
				cnt = remaintotalbtn%20;
			}
			currentbtn = Integer.parseInt(pdto.getBtn());
		}
		
		Map<String, Integer> p = new HashMap<String, Integer>();
		p.put("first", first);
		p.put("cnt", cnt);
		
		pdto.setP(p);
		
		model.addAttribute("see", 2); // 메세지 띄우기
		List<CompanyDTO> list = service.selectList(pdto); // list 변수에 결과 값을 담는다
        model.addAttribute("companyList", list); // model에 데이터 값을 담는다
        model.addAttribute("totalbtn", totalbtn);
        model.addAttribute("currentbtn", currentbtn);
        model.addAttribute("remaintotalbtn", remaintotalbtn);
        model.addAttribute("company_name", (String)session.getAttribute("company_name"));
		return "index";
	}
	
	// 삭제하기
	@RequestMapping(value="deleteCom")
	public String deleteCom(Model model, @RequestParam("param") String param, PagingDTO pdto, HttpSession session) throws Exception{
		
		String [] arr = param.split(" ");
		for(int i=0; i<arr.length; i++) {
			service.deleteCom(arr[i]);
		}
		
		int first = 0;
		int cnt = 20;
		int totalbtn = service.cntList(); // 총 버튼 수
		int remaintotalbtn = service.cntList(); // 계속 남아있는 총 버튼 수
		int currentbtn = 1;
		
		if(totalbtn%cnt!=0) {
			totalbtn = (totalbtn/cnt) + 1;
		}else {
			totalbtn = totalbtn/cnt;
		}
		
		// 2페이지 이상을 클릭했을때
		if(pdto.getBtn()!="" && pdto.getBtn()!=null && Integer.parseInt(pdto.getBtn())!=1) {
			first = (Integer.parseInt(pdto.getBtn())-1)*cnt;
			// 마지막 페이지
			if(Integer.parseInt(pdto.getBtn())==totalbtn && remaintotalbtn%cnt!=0) {
				cnt = remaintotalbtn%20;
			}
			currentbtn = Integer.parseInt(pdto.getBtn());
		}
		
		Map<String, Integer> p = new HashMap<String, Integer>();
		p.put("first", first);
		p.put("cnt", cnt);
		
		pdto.setP(p);
		
		model.addAttribute("msg", "삭제완료되었습니다.");
		model.addAttribute("see", 2); // 메세지 띄우기
		List<CompanyDTO> list = service.selectList(pdto); // list 변수에 결과 값을 담는다
        model.addAttribute("companyList", list); // model에 데이터 값을 담는다
        model.addAttribute("totalbtn", totalbtn);
        model.addAttribute("currentbtn", currentbtn);
        model.addAttribute("remaintotalbtn", remaintotalbtn);
        model.addAttribute("company_name", (String)session.getAttribute("company_name"));
		return "index";
	}
	
	// 메인
    @RequestMapping(value="main")
    public String main(Model model, PagingDTO pdto, HttpSession session) throws Exception {
    	
    	int first = 0; // 첫 시작 번호
		int cnt = 20; // 화면에 뿌려줄 갯수
		int totalbtn = service.cntList(); // 총 버튼 수
		int remaintotalbtn = service.cntList(); // 계속 남아있는 총 버튼 수
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
			if(Integer.parseInt(pdto.getBtn())==totalbtn && remaintotalbtn%20!=0) {
				cnt = remaintotalbtn%20;
			}
			// 현재 페이지
			currentbtn = Integer.parseInt(pdto.getBtn());
		}
		
		Map<String, Integer> p = new HashMap<String, Integer>();
		p.put("first", first);
		p.put("cnt", cnt);
		
		pdto.setP(p);
    	
    	List<CompanyDTO> list = service.selectList(pdto); // list 변수에 결과 값을 담는다
        model.addAttribute("companyList", list); // model에 데이터 값을 담는다
        model.addAttribute("see", 0); // 팝업 안보이게
        model.addAttribute("totalbtn", totalbtn);
        model.addAttribute("currentbtn", currentbtn);
        model.addAttribute("remaintotalbtn", remaintotalbtn);
        model.addAttribute("company_name", (String)session.getAttribute("company_name"));
        
        return "index"; // index.jsp로 이동
    }
    
    // 메인
    @RequestMapping(value="main2")
    public String main2(Model model, PagingDTO pdto) throws Exception {
        return "redirect:main?btn=" + pdto.getBtn(); // index.jsp로 이동
    }
	
}
























