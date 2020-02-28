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
package egovframework.example.sample.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.ExcelRead;
import egovframework.example.sample.service.ExcelReadOption;
import egovframework.example.sample.service.ExcelUtil;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Class Name : EgovSampleServiceImpl.java
 * @Description : Sample Business Implement Class
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

@Service("sampleService")
public class EgovSampleServiceImpl extends EgovAbstractServiceImpl implements EgovSampleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

	/** SampleDAO */
	// TODO ibatis 사용
//	@Resource(name = "sampleDAO")
//	private SampleDAO sampleDAO;
	// TODO mybatis 사용
	  @Resource(name="sampleMapper")
		private SampleMapper sampleDAO;

	/** ID Generation */
	@Resource(name = "egovIdGnrService")
	private EgovIdGnrService egovIdGnrService;

	/**
	 * 글을 등록한다.
	 * @param vo - 등록할 정보가 담긴 SampleVO
	 * @return 등록 결과
	 * @exception Exception
	 */
	@Override
	public String insertSample(SampleVO vo) throws Exception {
		LOGGER.debug(vo.toString());

		/** ID Generation Service */
		String id = egovIdGnrService.getNextStringId();
//		vo.setId(id);

//		int id = egovIdGnrService.getNextIntegerId();
//		vo.setId(id);
		
		LOGGER.debug(vo.toString());
		
		sampleDAO.insertSample(vo);
		return id;
		
	}

	/**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	 */
	@Override
	public void updateSample(SampleVO vo) throws Exception {
		sampleDAO.updateSample(vo);
	}

	/**
	 * 글을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	 */
	@Override
	public void deleteSample(SampleVO vo) throws Exception {
		sampleDAO.deleteSample(vo);
	}

	/**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 SampleVO
	 * @return 조회한 글
	 * @exception Exception
	 */
	@Override
	public SampleVO selectSample(SampleVO vo) throws Exception {
		SampleVO resultVO = sampleDAO.selectSample(vo);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}

	/**
	 * 글 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
	@Override
	public List<?> selectSampleList(SampleDefaultVO searchVO) throws Exception {
		return sampleDAO.selectSampleList(searchVO);
	}

	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	@Override
	public int selectSampleListTotCnt(SampleDefaultVO searchVO) {
		return sampleDAO.selectSampleListTotCnt(searchVO);
	}

	@Override
	public List<SampleVO> selectexcelList(SampleDefaultVO searchVO) throws Exception {
		System.out.println("@@@@#########@@@@@@#@#@#@#@@#@#@#"+sampleDAO.selectexcelList(searchVO));
		return sampleDAO.selectexcelList(searchVO);
	}


	  @Override
	  public List<Map> selectRow() throws Exception {

	    return sampleDAO.selectRow();
	  }
	
	  @Override
	  public void excelUpload(File destFile) {

	    ExcelReadOption excelReadOption = new ExcelReadOption();

//			파일경로 추가
	        excelReadOption.setFilePath(destFile.getAbsolutePath());
//	      추출할 컬럼 명 추가
	        excelReadOption.setOutputColumns("A","B","C","D");
	        // 시작 행
	        excelReadOption.setStartRow(1);

	        List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("excelContent", excelContent);

	        try {
	        	sampleDAO.insertExcel(paramMap);
	    } catch (Exception e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	  }

	  
	  
	  
	  
	  
	  public List<?> getExcelUpload(String excelFile){
		  
	        System.out.println("@@@@@@@@@@@@@@@getExcelUpload START@@@@@@@@@@@@@@@ "+excelFile);
	        System.out.println("@@@@@@@@@@@@@@@getExcelUpload START@@@@@@@@@@@@@@@ ");
	        
	        Map<String, Object> map = new HashMap<String, Object>();
	        List<?> list = null;
	        
	        try {
//	            Workbook wbs = WorkbookFactory.create(new FileInputStream(excelFile));
	            Workbook wbs = ExcelUtil.getWorkbook(excelFile);
	            
	            Sheet sheet = (Sheet) wbs.getSheetAt(0);
	 
	            //excel file 두번쨰줄부터 시작
	            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
	                
	            	System.out.println("@@@@@@@@map @@@@@@@@@@@@@@@@ i : "+i);
	                
	                Row row = sheet.getRow(i);
	                
	                //map.put("IDCOL", ""+ExcelUtil.cellValue(row.getCell(0)));
//	                map.put("ID", ""+ExcelUtil.cellValue(row.getCell(0)));
//	                map.put("history", ""+ExcelUtil.cellValue(row.getCell(2)));
//	                map.put("amount", ""+ExcelUtil.cellValue(row.getCell(3)));
//	                map.put("authorization", ""+ExcelUtil.cellValue(row.getCell(4)));
//	                map.put("processing", ""+ExcelUtil.cellValue(row.getCell(2)));
	                
	                
//	                map.put("usedate", ""+ExcelUtil.cellValue(row.getCell(1)));
//	                map.put("history", ""+ExcelUtil.cellValue(row.getCell(2)));
//	                map.put("processing", ""+ExcelUtil.cellValue(row.getCell(3)));
//	                map.put("registration", ""+ExcelUtil.cellValue(row.getCell(4)));
	                
//	                map.put("excelContent", ""+ExcelUtil.cellValue(row.getCell(4)));
	                
	                
//	                map.put("authorization", ""+ExcelUtil.cellValue(row.getCell(4)));
//	                map.put("processing", ""+ExcelUtil.cellValue(row.getCell(5)));
//	                map.put("registration", ""+ExcelUtil.cellValue(row.getCell(5)));
	                
	                
	                
	                
	                //신규삽입
	                sampleDAO.insertDB(map);
	            }
	 
	            System.out.println("@@@@@@@@map @@@@@@@@@@@@@@@@"+map.toString());
	            //데이터가져옵니다.
	            list = sampleDAO.testDbList(map);
	            
	        }catch(Exception e){
	        	System.out.println("error : "+e.getMessage());
	        	System.out.println("error : "+e);
	        }
	        System.out.println("@@@@@@@@@@@@@@@getExcelUpload END@@@@@@@@@@@@@@@");
	        return list;
	        
	    }





	
	






}
