package com.yuhan.test.service;

import java.awt.Font;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yuhan.test.dto.CompanyDTO;
import com.yuhan.test.dto.PagingDTO;
import com.yuhan.test.service.mapper.CompanyMapper;

@Service
public class CompanyService {
	
	@Autowired
    private CompanyMapper mapper;

	// 전체 리스트
	public List<CompanyDTO> selectList(PagingDTO pdto) throws Exception {
		return mapper.selectList(pdto);
	}
	
	// 전체 리스트(페이징X)
	public List<CompanyDTO> selectComList() throws Exception {
		return mapper.selectComList();
	}
	
	// 전체 리스트 갯수
	public int cntList() throws Exception {
		return mapper.cntList();
	}
	
	// 상세보기
	public CompanyDTO selectOne(String company_code) throws Exception {
		return mapper.selectOne(company_code);
	}
	
	// 수정하기
	public void updateCom(CompanyDTO cdto) throws Exception {
		mapper.updateCom(cdto);
	}
	
	// 추가하기
	public void insertCom(CompanyDTO cdto) throws Exception {
		mapper.insertCom(cdto);
	}
	
	// company_code 중복체크
	public int multiChk(String company_code) throws Exception{
		return mapper.multiChk(company_code);
	}
	
	// 삭제하기
	public void deleteCom(String company_code) throws Exception{
		mapper.deleteCom(company_code);
	}
	
	 /**
     * @param list
     * @return 생성된 워크북
     */
	// 다운로드
    public SXSSFWorkbook makeSimpleFruitExcelWorkbook(List<CompanyDTO> list) {
    	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        
        // 시트 생성
        SXSSFSheet sheet = workbook.createSheet("거래처 표");
        
        //시트 열 너비 설정
        sheet.setColumnWidth(0, 1500); //번호
        sheet.setColumnWidth(1, 2500); //사용여부
        sheet.setColumnWidth(2, 3500); //거래처
        sheet.setColumnWidth(3, 8000); //거래처명
        sheet.setColumnWidth(4, 9000); //거래처명(영문)
        sheet.setColumnWidth(5, 12000); //주소
        sheet.setColumnWidth(6, 12000); //주소(영문)
        sheet.setColumnWidth(7, 12000); //주소A(영문)
        sheet.setColumnWidth(8, 12000); //주소B(영문)
        sheet.setColumnWidth(9, 5000); //전화번호
        sheet.setColumnWidth(10, 5000); //팩스번호
        sheet.setColumnWidth(11, 6000); //담당자명(영문)
        sheet.setColumnWidth(12, 6000); //이메일
        sheet.setColumnWidth(13, 4000); //local담당자
        sheet.setColumnWidth(14, 4000); //local연락처
        sheet.setColumnWidth(15, 4000); //local email
        sheet.setColumnWidth(16, 4000); //비고사항
        sheet.setColumnWidth(17, 4000); //정산처
        sheet.setColumnWidth(18, 3500); //통화단위
        sheet.setColumnWidth(19, 5000); //사용자번호
        sheet.setColumnWidth(20, 5000); //대표자 이름
        sheet.setColumnWidth(21, 5000); //거래시작일
        sheet.setColumnWidth(22, 5000); //거래종료일
        sheet.setColumnWidth(23, 5000); //입력일자
        sheet.setColumnWidth(24, 5000); //입력자
        sheet.setColumnWidth(25, 5000); //수정일자
        sheet.setColumnWidth(26, 5000); //수정자
        sheet.setColumnWidth(27, 5000); //비밀번호
        
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);  //가운데 정렬
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(true); // 글씨 굵게
        style.setFont(font);
        style.setFillForegroundColor(HSSFColorPredefined.LEMON_CHIFFON.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 배경색
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN); // 가는 선 테두리
        
        CellStyle style1 = workbook.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);  //가운데 정렬
        style1.setBorderTop(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN); // 가는 선 테두리
        
        CellStyle style2 = workbook.createCellStyle();
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN); // 가는 선 테두리
      
        // 헤더 행 생
        Row headerRow = sheet.createRow(0);
        // 해당 행의 첫번째 열 셀 생성
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("번호");
        // 해당 행의 두번째 열 셀 생성
        headerCell = headerRow.createCell(1);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("사용여부");
        // 해당 행의 세번째 열 셀 생성
        headerCell = headerRow.createCell(2);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("거래처");
        // 해당 행의 네번째 열 셀 생성
        headerCell = headerRow.createCell(3);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("거래처명");
        headerCell = headerRow.createCell(4);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("거래처명(영문)");
        headerCell = headerRow.createCell(5);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("주소");
        headerCell = headerRow.createCell(6);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("주소(영문)");
        headerCell = headerRow.createCell(7);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("주소 A(영문)");
        headerCell = headerRow.createCell(8);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("주소 B(영문)");
        headerCell = headerRow.createCell(9);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("전화번호");
        headerCell = headerRow.createCell(10);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("팩스번호");
        headerCell = headerRow.createCell(11);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("담당자명(영문)");
        headerCell = headerRow.createCell(12);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("이메일");
        headerCell = headerRow.createCell(13);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Local 담당자");
        headerCell = headerRow.createCell(14);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Local 연락처");
        headerCell = headerRow.createCell(15);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Local Email");
        headerCell = headerRow.createCell(16);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("비고사항");
        headerCell = headerRow.createCell(17);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("정산처");
        headerCell = headerRow.createCell(18);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("통화단위");
        headerCell = headerRow.createCell(19);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("사용자번호");
        headerCell = headerRow.createCell(20);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("대표자 이름");
        headerCell = headerRow.createCell(21);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("거래시작일");
        headerCell = headerRow.createCell(22);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("거래종료일");
        headerCell = headerRow.createCell(23);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("입력일자");
        headerCell = headerRow.createCell(24);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("입력자");
        headerCell = headerRow.createCell(25);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("수정일자");
        headerCell = headerRow.createCell(26);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("수정자");
        headerCell = headerRow.createCell(27);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("비밀번호");
        
        // 과일표 내용 행 및 셀 생성
        Row bodyRow = null;
        Cell bodyCell = null;
        for(int i=0; i<list.size(); i++) {
            CompanyDTO cdto = list.get(i);
            
            // 행 생성
            bodyRow = sheet.createRow(i+1);
            // 데이터 번호 표시
            bodyCell = bodyRow.createCell(0);
            bodyCell.setCellStyle(style1);
            bodyCell.setCellValue(i + 1);
            // 데이터 이름 표시
            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellStyle(style1);
            bodyCell.setCellValue(cdto.getUse_check());
            // 데이터 가격 표시
            bodyCell = bodyRow.createCell(2);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getCompany_code());
            // 데이터 수량 표시
            bodyCell = bodyRow.createCell(3);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getCompany_name());
            bodyCell = bodyRow.createCell(4);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getCompany_name_eng());
            bodyCell = bodyRow.createCell(5);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getAddress());
            bodyCell = bodyRow.createCell(6);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getAddress_eng());
            bodyCell = bodyRow.createCell(7);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getAddressA_eng());
            bodyCell = bodyRow.createCell(8);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getAddressB_eng());
            bodyCell = bodyRow.createCell(9);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getTel());
            bodyCell = bodyRow.createCell(10);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getFex());
            bodyCell = bodyRow.createCell(11);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getManager_eng());
            bodyCell = bodyRow.createCell(12);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getEmail());
            bodyCell = bodyRow.createCell(13);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getLocal_manager());
            bodyCell = bodyRow.createCell(14);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getLocal_tel());
            bodyCell = bodyRow.createCell(15);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getLocal_email());
            bodyCell = bodyRow.createCell(16);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getRemark());
            bodyCell = bodyRow.createCell(17);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getSettlement());
            bodyCell = bodyRow.createCell(18);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getCur());
            bodyCell = bodyRow.createCell(19);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getBusinessNum());
            bodyCell = bodyRow.createCell(20);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getPresident());
            bodyCell = bodyRow.createCell(21);
            bodyCell.setCellStyle(style1);
            bodyCell.setCellValue(cdto.getStart_date());
            bodyCell = bodyRow.createCell(22);
            bodyCell.setCellStyle(style1);
            bodyCell.setCellValue(cdto.getEnd_date());
            bodyCell = bodyRow.createCell(23);
            bodyCell.setCellStyle(style1);
            String date = cdto.getInput_date();
            String[] arrDate = date.split(" ");
            bodyCell.setCellValue(arrDate[0]);
            bodyCell = bodyRow.createCell(24);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getInpuner());
            bodyCell = bodyRow.createCell(25);
            bodyCell.setCellStyle(style1);
            if(cdto.getModify_date()!=null) {
            	date = cdto.getModify_date();
                String[] arrDate2 = date.split(" ");
                bodyCell.setCellValue(arrDate2[0]);
            }else {
            	bodyCell.setCellValue(cdto.getModify_date());
            }
            bodyCell = bodyRow.createCell(26);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getModifier());
            bodyCell = bodyRow.createCell(27);
            bodyCell.setCellStyle(style2);
            bodyCell.setCellValue(cdto.getCom_password());
        }
        
        return workbook;
    }
    
    /**
     * 생성한 엑셀 워크북을 컨트롤레에서 받게해줄 메소
     * @param list
     * @return
     */
    public SXSSFWorkbook excelFileDownloadProcess(List<CompanyDTO> list) {
        return this.makeSimpleFruitExcelWorkbook(list);
    }
    
    // 엑셀 셈플 다운로드
    private SXSSFWorkbook makeSimpleFruitExcelSampleWorkbook() {
    	SXSSFWorkbook workbook = new SXSSFWorkbook();
        
        // 시트 생성
        SXSSFSheet sheet = workbook.createSheet("거래처 표");
        
        //시트 열 너비 설정
        sheet.setColumnWidth(0, 1500); //번호
        sheet.setColumnWidth(1, 2500); //사용여부
        sheet.setColumnWidth(2, 3500); //거래처
        sheet.setColumnWidth(3, 8000); //거래처명
        sheet.setColumnWidth(4, 9000); //거래처명(영문)
        sheet.setColumnWidth(5, 12000); //주소
        sheet.setColumnWidth(6, 12000); //주소(영문)
        sheet.setColumnWidth(7, 12000); //주소A(영문)
        sheet.setColumnWidth(8, 12000); //주소B(영문)
        sheet.setColumnWidth(9, 5000); //전화번호
        sheet.setColumnWidth(10, 5000); //팩스번호
        sheet.setColumnWidth(11, 6000); //담당자명(영문)
        sheet.setColumnWidth(12, 6000); //이메일
        sheet.setColumnWidth(13, 4000); //local담당자
        sheet.setColumnWidth(14, 4000); //local연락처
        sheet.setColumnWidth(15, 4000); //local email
        sheet.setColumnWidth(16, 4000); //비고사항
        sheet.setColumnWidth(17, 4000); //정산처
        sheet.setColumnWidth(18, 3500); //통화단위
        sheet.setColumnWidth(19, 5000); //사용자번호
        sheet.setColumnWidth(20, 5000); //대표자 이름
        sheet.setColumnWidth(21, 5000); //거래시작일
        sheet.setColumnWidth(22, 5000); //거래종료일
        sheet.setColumnWidth(23, 5000); //비밀번호
        
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);  //가운데 정렬
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(true); // 글씨 굵게
        style.setFont(font);
        style.setFillForegroundColor(HSSFColorPredefined.LEMON_CHIFFON.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 배경색
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN); // 가는 선 테두리
      
        // 헤더 행 생
        Row headerRow = sheet.createRow(0);
        // 해당 행의 첫번째 열 셀 생성
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("번호");
        // 해당 행의 두번째 열 셀 생성
        headerCell = headerRow.createCell(1);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("사용여부*");
        // 해당 행의 세번째 열 셀 생성
        headerCell = headerRow.createCell(2);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("거래처*");
        // 해당 행의 네번째 열 셀 생성
        headerCell = headerRow.createCell(3);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("거래처명*");
        headerCell = headerRow.createCell(4);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("거래처명(영문)*");
        headerCell = headerRow.createCell(5);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("주소");
        headerCell = headerRow.createCell(6);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("주소(영문)");
        headerCell = headerRow.createCell(7);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("주소 A(영문)");
        headerCell = headerRow.createCell(8);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("주소 B(영문)");
        headerCell = headerRow.createCell(9);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("전화번호");
        headerCell = headerRow.createCell(10);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("팩스번호");
        headerCell = headerRow.createCell(11);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("담당자명(영문)");
        headerCell = headerRow.createCell(12);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("이메일");
        headerCell = headerRow.createCell(13);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Local 담당자");
        headerCell = headerRow.createCell(14);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Local 연락처");
        headerCell = headerRow.createCell(15);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("Local Email");
        headerCell = headerRow.createCell(16);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("비고사항");
        headerCell = headerRow.createCell(17);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("정산처");
        headerCell = headerRow.createCell(18);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("통화단위");
        headerCell = headerRow.createCell(19);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("사용자번호");
        headerCell = headerRow.createCell(20);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("대표자 이름");
        headerCell = headerRow.createCell(21);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("거래시작일");
        headerCell = headerRow.createCell(22);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("거래종료일");
        headerCell = headerRow.createCell(23);
        headerCell.setCellStyle(style);
        headerCell.setCellValue("비밀번호*");
        
        return workbook;
	}
    
    public SXSSFWorkbook excelFileDownloadSampleProcess() {
        return this.makeSimpleFruitExcelSampleWorkbook();
    }

	// 업로드
    public String uploadExcelFile(MultipartFile excelFile, String id){
    	String frontContent = ""; // 오류 내역 + 메세지 내역
    	StringBuffer content = new StringBuffer(); // 메세지 내역
        int msgchk = 0; // 각 행의 오류 체크
        int totalError = 0; // 총 오류 갯수
        ArrayList<Integer> badLine = new ArrayList<Integer>(); // 오류난 행들
        Date date = new Date();
    	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = transFormat.format(date);
    	
        try {
            OPCPackage opcPackage = OPCPackage.open(excelFile.getInputStream());
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
            
            // 첫번째 시트 불러오기
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            for(int i=1; i<sheet.getLastRowNum() + 1; i++) {
            	CompanyDTO cdto = new CompanyDTO();
                XSSFRow row = sheet.getRow(i);
                
                if(row == null) {
                	content.append("[" + i + "]행 전체 빈값\n\n");
            		totalError++;
            		badLine.add(i);
            		continue;
                }
                
                // 행의 두번째 열(이름부터 받아오기) 
                XSSFCell cell = row.getCell(1);
                if(null != cell) {
                	boolean flag = Pattern.matches("^[Y|N]*$", getStringValue(cell)); 
                	if(flag==false) {
                		content.append("[" + i + "]행 [사용여부 오류]: 대문자 Y와 N만 입력가능\n");
                		msgchk++;
                		totalError++;
                	}else if(getStringValue(cell).length()>1) {
                		content.append("[" + i + "]행 [사용여부 오류]: 글자수 초과(1글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setUse_check(getStringValue(cell));
                	}
                }else {
                	content.append("[" + i + "]행 [사용여부 오류]: null 값 불가\n");
            		msgchk++;
            		totalError++;
                }
                // 행의 세번째 열 받아오기
                cell = row.getCell(2);
                if(null != cell) {
                	if(multiChk(getStringValue(cell))==1) {
                		content.append("[" + i + "]행 [회사코드 오류]: 중복!\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setCompany_code(getStringValue(cell));
                	}
                }else {
                	content.append("[" + i + "]행 [회사코드 오류]: null 값 불가\n");
            		msgchk++;
            		totalError++;
                }
                // 행의 네번째 열 받아오기
                cell = row.getCell(3);
                if(null != cell) {
                	if(getStringValue(cell).length()>25) {
                		content.append("[" + i + "]행 [거래처명 오류]: 글자수 초과(25글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setCompany_name(getStringValue(cell));
                	}
                }else {
                	content.append("[" + i + "]행 [거래처명 오류]: null 값 불가\n");
            		msgchk++;
            		totalError++;
                }
                cell = row.getCell(4);
                if(null != cell) {
                	boolean flag = Pattern.matches("^[a-zA-Z0-9| |,|''-|.]*$", getStringValue(cell)); 

                	if(flag==false) {
                		content.append("[" + i + "]행 [거래처명(영어) 오류]: 영어와 숫자만 입력 가능!\n");
                		msgchk++;
                		totalError++;
                	}else if(getStringValue(cell).length()>25) {
                		content.append("[" + i + "]행 [거래처명(영어)]: 글자수 초과(25글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setCompany_name_eng(getStringValue(cell));
                	}
                }else {
                	content.append("[" + i + "]행 [거래처명(영어) 오류]: null 값 불가\n");
            		msgchk++;
            		totalError++;
                }
                cell = row.getCell(5);
                if(null != cell) {
                	if(getStringValue(cell).length()>100) {
                		content.append("[" + i + "]행 [주소 오류]: 글자수 초과(100글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setAddress(getStringValue(cell));
                	}
                }
                cell = row.getCell(6);
                if(null != cell) {
                	boolean flag = Pattern.matches("^[a-zA-Z0-9| |,|''-|.]*$", getStringValue(cell)); 

                	if(flag==false) {
                		content.append("[" + i + "]행 [주소(영어) 오류]: 영어와 숫자만 입력 가능!\n");
                		msgchk++;
                		totalError++;
                	}else if(getStringValue(cell).length()>100) {
                		content.append("[" + i + "]행 [주소(영어) 오류]: 글자수 초과(25글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setAddress_eng(getStringValue(cell));
                	}
                }
                cell = row.getCell(7);
                if(null != cell) {
                	boolean flag = Pattern.matches("^[a-zA-Z0-9| |,|''-|.]*$", getStringValue(cell)); 

                	if(flag==false) {
                		content.append("[" + i + "]행 [주소A(영어) 오류]: 영어와 숫자만 입력 가능!\n");
                		msgchk++;
                		totalError++;
                	}else if(getStringValue(cell).length()>50) {
                		content.append("[" + i + "]행 [주소A(영어) 오류]: 글자수 초과(50글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setAddressA_eng(getStringValue(cell));
                	}
                }
                cell = row.getCell(8);
                if(null != cell) {
                	boolean flag = Pattern.matches("^[a-zA-Z0-9| |,|''-|.]*$", getStringValue(cell)); 

                	if(flag==false) {
                		content.append("[" + i + "]행 [주소B(영어) 오류]: 영어와 숫자만 입력 가능!\n");
                		msgchk++;
                		totalError++;
                	}else if(getStringValue(cell).length()>50) {
                		content.append("[" + i + "]행 [주소B(영어) 오류]: 글자수 초과(50글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setAddressB_eng(getStringValue(cell));
                	}
                }
                cell = row.getCell(9);
                if(null != cell) {
                	if(getStringValue(cell).length()>15) {
                		content.append("[" + i + "]행 [휴대폰번호 오류]: 글자수 초과(15글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setTel(getStringValue(cell));
                	}
                }
                cell = row.getCell(10);
                if(null != cell) {
                	if(getStringValue(cell).length()>15) {
                		content.append("[" + i + "]행 [팩스번호 오류]: 글자수 초과(15글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setFex(getStringValue(cell));
                	}
                }
                cell = row.getCell(11);
                if(null != cell) {
                	boolean flag = Pattern.matches("^[a-zA-Z0-9| |,|''-|.]*$", getStringValue(cell)); 

                	if(flag==false) {
                		content.append("[" + i + "]행 [담당자명(영어) 오류]: 영어와 숫자만 입력 가능!\n");
                		msgchk++;
                		totalError++;
                	}else if(getStringValue(cell).length()>20) {
                		content.append("[" + i + "]행 [담당자명(영어) 오류]: 글자수 초과(20글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setManager_eng(getStringValue(cell));
                	}
                }
                cell = row.getCell(12);
                if(null != cell) {
                	if(getStringValue(cell).length()>20) {
                		content.append("[" + i + "]행 [이메일 오류]: 글자수 초과(20글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setEmail(getStringValue(cell));
                	}
                }
                cell = row.getCell(13);
                if(null != cell) {
                	if(getStringValue(cell).length()>20) {
                		content.append("[" + i + "]행 [Local담당자 오류]: 글자수 초과(20글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setLocal_manager(getStringValue(cell));
                	}
                }
                cell = row.getCell(14);
                if(null != cell) {
                	if(getStringValue(cell).length()>20) {
                		content.append("[" + i + "]행 [Local연락처 오류]: 글자수 초과(20글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setLocal_tel(getStringValue(cell));
                	}
                }
                cell = row.getCell(15);
                if(null != cell) {
                	if(getStringValue(cell).length()>20) {
                		content.append("[" + i + "]행 [Local email 오류]: 글자수 초과(20글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setLocal_email(getStringValue(cell));
                	}
                }
                cell = row.getCell(16);
                if(null != cell) {
                	if(getStringValue(cell).length()>20) {
                		content.append("[" + i + "]행 [비고사항 오류]: 글자수 초과(20글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setRemark(getStringValue(cell));
                	}
                }
                cell = row.getCell(17);
                if(null != cell) {
                	if(getStringValue(cell).length()>10) {
                		content.append("[" + i + "]행 [정산처 오류]: 글자수 초과(10글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setSettlement(getStringValue(cell));
                	}
                }
                cell = row.getCell(18);
                if(null != cell) {
                	boolean flag = Pattern.matches("^[a-zA-Z]*$", getStringValue(cell)); 

                	if(flag==false) {
                		content.append("[" + i + "]행 [통화단위 오류]: 영어만 입력 가능!\n");
                		msgchk++;
                		totalError++;
                	}else if(getStringValue(cell).length()>3) {
                		content.append("[" + i + "]행 [통화단위 오류]: 글자수 초과(3글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setCur(getStringValue(cell));
                	}
                }
                cell = row.getCell(19);
                if(null != cell) {
                	boolean flag = Pattern.matches("^[0-9]*$", getStringValue(cell)); 

                	if(flag==false) {
                		content.append("[" + i + "]행 [사용자번호 오류]: 숫자만 입력 가능!\n");
                		msgchk++;
                		totalError++;
                	}else if(getStringValue(cell).length()>15) {
                		content.append("[" + i + "]행 [사용자번호 오류]: 글자수 초과(15글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setBusinessNum(getStringValue(cell));
                	}
                }
                cell = row.getCell(20);
                if(null != cell) {
                	if(getStringValue(cell).length()>20) {
                		content.append("[" + i + "]행 [대표자 이름 오류]: 글자수 초과(20글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setPresident(getStringValue(cell));
                	}
                }
                cell = row.getCell(21);
                if(null != cell) {
                	boolean flag = Pattern.matches("^(19|20|21){2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$", getStringValue2(cell)); 
                	System.out.println("getStringValue2(cell): " + getStringValue2(cell));
                	System.out.println("flag: " + flag);
                	if(flag==false) {
                		content.append("[" + i + "]행 [거래시작일 오류]: 날자 형식에 맞게 입력\n");
                		msgchk++;
                		totalError++;
                	}else if(getStringValue2(cell).length()>10) {
                		content.append("[" + i + "]행 [거래시작일 오류]: 글자수 초과(10글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setStart_date(getStringValue2(cell));
                	}
                }
                cell = row.getCell(22);
                if(null != cell) {
                	boolean flag = Pattern.matches("^(19|20|21){2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$", getStringValue2(cell)); 
                	
                	if(flag==false) {
                		content.append("[" + i + "]행 [거래종료일 오류]: 날자 형식에 맞게 입력\n");
                		msgchk++;
                		totalError++;
                	}else if(getStringValue2(cell).length()>10) {
                		content.append("[" + i + "]행 [거래종료일 오류]: 글자수 초과(10글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setEnd_date(getStringValue2(cell));
                	}
                }
                cell = row.getCell(23);
                if(null != cell) {
                	if(getStringValue(cell).length()>20) {
                		content.append("[" + i + "]행 [비밀번호 오류]: 글자수 초과(20글자 이상X)\n");
                		msgchk++;
                		totalError++;
                	}else {
                		cdto.setCom_password(getStringValue(cell));
                	}
                }else {
                	content.append("[" + i + "]행 [비밀번호 오류]: null 값 불가\n");
            		msgchk++;
            		totalError++;
                }
                
                /*// 행의 세번째 열 받아오기
                cell = row.getCell(2);
                if(null != cell) fruit.setPrice((long)cell.getNumericCellValue());
                // 행의 네번째 열 받아오기
                cell = row.getCell(3);
                if(null != cell) fruit.setQuantity((int)cell.getNumericCellValue());*/
                
                System.out.println(i + " msgchk: " + msgchk);
                System.out.println(i + " totalError: " + totalError);
                System.out.println(i + " content: " + content);
                
                // 오류가 없을떄 | 있을때
                if(msgchk==0) {
                	System.out.println("I: " + i);
                	cdto.setInput_date(now);
                	cdto.setInpuner(id);
                	insertCom(cdto);
                }else {
                	content.append("\n");
                	msgchk = 0;
                	badLine.add(i);
                }
            }
            
            if(sheet.getLastRowNum()==0) {
            	frontContent = "읽어들일 값이 존재하지 않습니다!";
            	NewTest(frontContent);
            }else if(totalError==0) {
            	frontContent = "업로드 완료!";
            }else {
            	frontContent = totalError + "건의 오류를 발견했습니다.\n" + badLine + "번째 행 오류\n============\n" + content;
                NewTest(frontContent);
                frontContent = totalError + "건의 오류를 발견했습니다.\n" + badLine + "번째 행 오류";
            }
            
        } catch (Exception e) {
        	frontContent = "엑셀 파일을 넣어주세요!";
            e.printStackTrace();
        }
        return frontContent;
    }
    
    // text file 생성
 	public void NewTest(String content){
 		Date date = new Date();
         SimpleDateFormat dayformat = new SimpleDateFormat("yyyyMMdd");
         SimpleDateFormat hourformat = new SimpleDateFormat("hhmmss");
         String day = dayformat.format(date);
         String hour = hourformat.format(date);
         String filePath = "C:\\Users\\Q10003\\Downloads\\오류내역_" + day + "_" + hour + ".text";
         try {
         	FileWriter fileWriter = new FileWriter(filePath);
         	fileWriter.write(content);
         	fileWriter.close();
         }catch(IOException e) {
         	e.printStackTrace();
         }
 	}
    
    // 예외처리(int -> string으로!)
    public static String getStringValue(Cell cell) {
        String rtnValue = "";
        try {
            rtnValue = cell.getStringCellValue();
        } catch(IllegalStateException e) {
            rtnValue = Integer.toString((int)cell.getNumericCellValue());            
        }
        
        return rtnValue;
    }
    
    // 예외처리(date -> string으로!)
    public static String getStringValue2(Cell cell) {
        String rtnValue = "";
        try {
            rtnValue = cell.getStringCellValue();
        } catch(IllegalStateException e) {
        	Date date = cell.getDateCellValue();
        	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        	rtnValue = transFormat.format(date);   
        }
        
        return rtnValue;
    }


	
}



























