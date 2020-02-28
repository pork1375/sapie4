/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.example.sample.web;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : EgovSampleController.java
 * @Description : EgovSample Controller Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Controller
public class EgovSampleController {

	/** EgovSampleService */
	@Resource(name = "sampleService")
	private EgovSampleService sampleService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	/**
	 * 글 목록을 조회한다. (pageing)
	 * @param searchVO - 조회할 정보가 담긴 SampleDefaultVO
	 * @param model
	 * @return "egovSampleList"
	 * @exception Exception
	 */
	@RequestMapping(value = "/egovSampleList.do")
	public String selectSampleList(@ModelAttribute("searchVO") SampleDefaultVO searchVO, ModelMap model) throws Exception {

		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));
		
		// 월별검색 
//		System.out.println("@@@@@@searchVO.getSearchCondition2();@@@@@@@@"+searchVO.getSearchCondition2());
		

		/** pageing setting */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<?> sampleList = sampleService.selectSampleList(searchVO);
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@resultList"+sampleList); // 리스트가 잘 들어가는지 체크
		model.addAttribute("resultList", sampleList);

		int totCnt = sampleService.selectSampleListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("totCnt", totCnt);

		return "sample/egovSampleList";
	}
	
	// 엑셀 다운로드
	@RequestMapping(value="/excelDown.do")
	  public void excelDown(HttpServletResponse response, Model model,
			  @RequestParam(defaultValue = "test") String fileName,
			  @ModelAttribute("searchVO") SampleDefaultVO searchVO) throws Exception {

	      HSSFWorkbook objWorkBook = new HSSFWorkbook();
	      HSSFSheet objSheet = null;
	      HSSFRow objRow = null;
	      HSSFCell objCell = null;       //셀 생성

	        //제목 폰트
	  HSSFFont font = objWorkBook.createFont(); 
	  font.setFontHeightInPoints((short)9);		// 글자 크기 설정
	  font.setBoldweight((short)font.BOLDWEIGHT_BOLD);	// 글자 굵게 하기
	  font.setFontName("맑은고딕");	// 폰트 설정

	  //제목 스타일에 폰트 적용, 정렬
	  HSSFCellStyle styleHd = objWorkBook.createCellStyle();    //제목 스타일 생성
	  styleHd.setFont(font);	// 만들어 놓은 폰트 적용
	  styleHd.setAlignment(HSSFCellStyle.ALIGN_CENTER);	// 가운데 정렬 설정
	  styleHd.setVerticalAlignment (HSSFCellStyle.VERTICAL_CENTER);	// 수직 중앙 정렬 설정 데이터 입력하는 곳

	  objSheet = objWorkBook.createSheet("첫번째 시트");     //워크시트 생성
	  
	  List<SampleVO> sampleList = sampleService.selectexcelList(searchVO);	// 새로 만들어서 샘플 리스트에 담아 출력!!!
	  System.out.println("@@@@@@@sampleList@@@@@@@@@"+sampleList);
	  
	  
	  
	  // 1행
	  objRow = objSheet.createRow(0);
	  objRow.setHeight ((short) 0x300);	 // 행 높이

	  // 본격적인 데이터 넣기
	  objCell = objRow.createCell(0);
	  objCell.setCellValue("번호");
	  objCell.setCellStyle(styleHd);

	  objCell = objRow.createCell(1);
	  objCell.setCellValue("사용일");
	  objCell.setCellStyle(styleHd);

	  objCell = objRow.createCell(2);
	  objCell.setCellValue("사용내역");
	  objCell.setCellStyle(styleHd);

	  objCell = objRow.createCell(3);
	  objCell.setCellValue("사용금액");
	  objCell.setCellStyle(styleHd);
	  
/*	  objCell = objRow.createCell(4);
	  objCell.setCellValue("승인금액");
	  objCell.setCellStyle(styleHd);*/
	  
	  objCell = objRow.createCell(4);
	  objCell.setCellValue("처리상태");
	  objCell.setCellStyle(styleHd);
	  
	  objCell = objRow.createCell(5);
	  objCell.setCellValue("등록일");
	  objCell.setCellStyle(styleHd);
	  
	  objCell = objRow.createCell(6);
	  objCell.setCellValue("비고");
	  objCell.setCellStyle(styleHd);
	  
	  
	  int index = 1;
	  for(SampleVO vo : sampleList) {
	    objRow = objSheet.createRow(index);
	    objRow.setHeight((short) 0x300);
		  
		objCell = objRow.createCell(0);
		objCell.setCellValue(vo.getUsedate());
		objCell.setCellStyle(styleHd);
		
		objCell = objRow.createCell(1);
		objCell.setCellValue(vo.getHistory());
		objCell.setCellStyle(styleHd);
		
		objCell = objRow.createCell(2);
		objCell.setCellValue(vo.getHistory());
		objCell.setCellStyle(styleHd);
		
		/*objCell = objRow.createCell(3);
		objCell.setCellValue(vo.getAmount());
		objCell.setCellStyle(styleHd);*/
		
		objCell = objRow.createCell(3);
		objCell.setCellValue(vo.getAuthorization());
		objCell.setCellStyle(styleHd);
		
		objCell = objRow.createCell(4);
		objCell.setCellValue(vo.getProcessing());
		objCell.setCellStyle(styleHd);
		
		objCell = objRow.createCell(5);
		objCell.setCellValue(vo.getRegistration());
		objCell.setCellStyle(styleHd);
		
		objCell = objRow.createCell(6);
		objCell.setCellValue(vo.getRemarks());
		objCell.setCellStyle(styleHd);
		
		
//		System.out.println("@@@@@@@@vovovovovovovovovovovovovovovo"+vo.getId());
		
		index++;
	  }
	  
	  for (int i = 0; i < sampleList.size(); i++) {
		    objSheet.autoSizeColumn(i);
		  }
	  
	  
	  
//	  objRow = objSheet.createRow(1);
//	  objRow.setHeight ((short) 0x150);
//
//	  objCell = objRow.createCell(0);
//	  objCell.setCellValue("1");
//	  objCell.setCellStyle(styleHd);
//
//	  objCell = objRow.createCell(1);
//	  objCell.setCellValue("홍길동");
//	  objCell.setCellStyle(styleHd);


	 
	  
	  response.setContentType("Application/Msexcel");
	  response.setHeader("Content-Disposition", "ATTachment; Filename="+URLEncoder.encode(fileName,"UTF-8")+".xls");

	  OutputStream fileOut  = response.getOutputStream();
	  objWorkBook.write(fileOut);
	  fileOut.close();

	  response.getOutputStream().flush();
	  response.getOutputStream().close();
	}
	
	// 엑셀 업로드
//	@RequestMapping(value = "/excelUp.do", method = RequestMethod.POST)
//    public void ExcelUp(HttpServletRequest req, HttpServletResponse rep){
//
// 
//        Map returnObject = new HashMap();
//        
//        try { // MultipartHttpServletRequest 생성 
//            MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) req; 
//            Iterator iter = mhsr.getFileNames(); 
//            MultipartFile mfile = null; 
//            String fieldName = ""; 
//            
//            // 값이 나올때까지
//            while (iter.hasNext()) {
//                fieldName = iter.next().toString(); // 내용을 가져와서 
//                mfile = mhsr.getFile(fieldName); 
//                String origName; 
//                origName = new String(mfile.getOriginalFilename().getBytes("8859_1"), "UTF-8"); //한글꺠짐 방지 // 파일명이 없다면 
//                
//                returnObject.put("params", mhsr.getParameterMap());
//                
//                
//                //위치 및 파일
//                sampleService.getExcelUpload("D:\\"+origName);
//            }
//            System.out.println("@@@@@@returnObject######"+returnObject);
//            System.out.println("@@@@@@mhsr.getParameterMap()######"+mhsr.getParameterMap());
//            
//            
//            } catch (UnsupportedEncodingException e) { // TODO Auto-generated catch block 
//                e.printStackTrace(); 
//            }catch (IllegalStateException e) { // TODO Auto-generated catch block 
//                e.printStackTrace(); 
//            } catch (IOException e) { // TODO Auto-generated catch block 
//                e.printStackTrace(); 
//            }
// 
//    }
	
	
	// 엑셀 업로드 2
	  @ResponseBody
	  @RequestMapping(value = "/excelUploadAjax.do", method = RequestMethod.POST)
	  public ModelAndView excelUploadAjax(MultipartFile testFile, MultipartHttpServletRequest request)  throws Exception{

	    System.out.println("업로드 진행");

	    MultipartFile excelFile = request.getFile("excelFile");

	    if(excelFile==null || excelFile.isEmpty()){

	        throw new RuntimeException("엑셀파일을 선택 해 주세요.");
	    }

	    File destFile = new File("C:\\"+excelFile.getOriginalFilename());

	    try{
	      //내가 설정한 위치에 내가 올린 파일을 만들고
	        excelFile.transferTo(destFile);

	    }catch(Exception e){
	        throw new RuntimeException(e.getMessage(),e);
	    }

	    //업로드를 진행하고 다시 지우기
	    sampleService.excelUpload(destFile);

	    destFile.delete();
	  //		FileUtils.delete(destFile.getAbsolutePath());

	    ModelAndView view = new ModelAndView();

	    view.setViewName("/egovSampleList.do");

	      return view;
	  }

	
	

	/**
	 * 글 등록 화면을 조회한다.
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	 */
	@RequestMapping(value = "/addSample1.do", method = RequestMethod.POST)
	public String addSampleView(@ModelAttribute("searchVO") SampleDefaultVO searchVO, Model model) throws Exception {
		model.addAttribute("sampleVO", new SampleVO());
		return "sample/egovSampleRegister";
	}

	/**
	 * 글을 등록한다.
	 * @param sampleVO - 등록할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	@RequestMapping(value = "/addSample.do", method = RequestMethod.POST)
	public String addSample(@ModelAttribute("searchVO") SampleDefaultVO searchVO,
			SampleVO sampleVO, BindingResult bindingResult, Model model, SessionStatus status,
			@RequestPart MultipartFile files)
			throws Exception {
		
		// @RequestPart MultipartFile files 파일 시작
		String sourceFileName = files.getOriginalFilename();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+sourceFileName);
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
        File destinationFile;
        String destinationFileName;
        String fileUrl = "C:\\eGovFrameDev-3.8.0-64bit\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\sapie\\upload\\";

        do {
            destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension; 
            destinationFile = new File(fileUrl + destinationFileName); 
            System.out.println("file경로"+destinationFile);
            sampleVO.setFileUrl(fileUrl+destinationFile);
            sampleVO.setFileOriName(sourceFileName);
            sampleVO.setFileName(destinationFileName);
        } while (destinationFile.exists());
        
        destinationFile.getParentFile().mkdirs(); 
        files.transferTo(destinationFile);
		//----- 파일 끝

		// Server-Side Validation
		beanValidator.validate(sampleVO, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("sampleVO", sampleVO);
			return "sample/egovSampleRegister";
		}

		sampleService.insertSample(sampleVO);
		status.setComplete();
		return "forward:/egovSampleList.do";
	}

	/**
	 * 글 수정화면을 조회한다.
	 * @param id - 수정할 글 id
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	 */
	@RequestMapping("/updateSampleView.do")
	public String updateSampleView(@RequestParam("selectedId") int id, @ModelAttribute("searchVO") SampleDefaultVO searchVO, Model model) throws Exception {
		SampleVO sampleVO = new SampleVO();
		sampleVO.setId(id);
		// 변수명은 CoC 에 따라 sampleVO
		model.addAttribute(selectSample(sampleVO, searchVO));
		return "sample/egovSampleRegister";
	}

	/**
	 * 글을 조회한다.
	 * @param sampleVO - 조회할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return @ModelAttribute("sampleVO") - 조회한 정보
	 * @exception Exception
	 */
	public SampleVO selectSample(SampleVO sampleVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO) throws Exception {
		return sampleService.selectSample(sampleVO);
	}

	/**
	 * 글을 수정한다.
	 * @param sampleVO - 수정할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	@RequestMapping("/updateSample.do")
	public String updateSample(@ModelAttribute("searchVO") SampleDefaultVO searchVO, SampleVO sampleVO, BindingResult bindingResult, Model model, SessionStatus status)
			throws Exception {

		beanValidator.validate(sampleVO, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("sampleVO", sampleVO);
			return "sample/egovSampleRegister";
		}

		sampleService.updateSample(sampleVO);
		status.setComplete();
		return "forward:/egovSampleList.do";
	}

	/**
	 * 글을 삭제한다.
	 * @param sampleVO - 삭제할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	@RequestMapping("/deleteSample.do")
	public String deleteSample(SampleVO sampleVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO, SessionStatus status) throws Exception {
		sampleService.deleteSample(sampleVO);
		status.setComplete();
		return "forward:/egovSampleList.do";
	}

}
