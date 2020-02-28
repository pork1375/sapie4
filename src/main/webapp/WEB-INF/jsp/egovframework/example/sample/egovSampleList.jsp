<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
  /**
  * @Class Name : egovSampleList.jsp
  * @Description : Sample List 화면
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2009.02.01            최초 생성
  *
  * author 실행환경 개발팀
  * since 2009.02.01
  *
  * Copyright (C) 2009 by MOPAS  All right reserved.
  */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
 <script src="https://code.jquery.com/jquery-1.12.4.js"></script>달력 
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> 
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="http://malsup.github.io/min/jquery.form.min.js"></script>
<style>
     #monthpicker {
			width: 60px;
		}
		#btn_monthpicker {
			background: url('./datepicker.png');
			border: 0;
			height: 24px;
			overflow: hieen;
			text-indent: 999;
			width: 24px;
		} 
    </style>
    
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><spring:message code="title.sample" /></title>
    <link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/sample.css'/>"/>

    <script type="text/javaScript" language="javascript" defer="defer">
        <!--
        /* 글 수정 화면 function */
        function fn_egov_select(id) {
        	document.listForm.selectedId.value = id;
           	document.listForm.action = "<c:url value='/updateSampleView.do'/>";
           	document.listForm.submit();
        }
        
        /* 글 등록 화면 function */
        function fn_egov_addView() {
           	document.listForm.action = "<c:url value='/addSample1.do'/>";
           	document.listForm.submit();
        }
        
        /* 글 목록 화면 function */
        function fn_egov_selectList() {
        	document.listForm.action = "<c:url value='/egovSampleList.do'/>";
           	document.listForm.submit();
        }
        
        /* pagination 페이지 링크 function */
        function fn_egov_link_page(pageNo){
        	document.listForm.pageIndex.value = pageNo;
        	document.listForm.action = "<c:url value='/egovSampleList.do'/>";
           	document.listForm.submit();
        }
        
        //-->
        // 달력
$(function(){
            $("#usedate").datepicker({
            	dateFormat: 'yy-mm' 
            	,dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'] // 요일의 한글 형식.
            	, monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] // 월의 한글 형식.
            	, showButtonPanel: true // 캘린더 하단에 버튼 패널을 표시한다. 
            	 , currentText: '오늘 날짜' // 오늘 날짜로 이동하는 버튼 패널
            	, closeText: '닫기'  // 닫기 버튼 패널
            	,changeMonth: true // 월을 바꿀수 있는 셀렉트 박스를 표시한다.
            	 ,changeYear: true // 년을 바꿀 수 있는 셀렉트 박스를 표시한다.
            });
})
</script>
        
            <script>
         // 엑셀 업로드
  function checkFileType(filePath) {
    var fileFormat = filePath.split(".");

    if (fileFormat.indexOf("xls") > -1 || fileFormat.indexOf("xlsx") > -1) {
      return true;
      } else {
      return false;
    }
  }

  function check() {

    var file = $("#excelFile").val();

    if (file == "" || file == null) {
    alert("파일을 선택해주세요.");

    return false;
    } else if (!checkFileType(file)) {
    alert("엑셀 파일만 업로드 가능합니다.");

    return false;
    }

    if (confirm("업로드 하시겠습니까?")) {

      var options = {

        success : function(data) {
          alert("모든 데이터가 업로드 되었습니다.");

        },
        type : "POST"
        };

      $("#excelUploadForm").ajaxSubmit(options);
    }
  }
  </script>
        
 
     


</head>

<body style="text-align:center; margin:0 auto; display:inline; padding-top:100px;">
    <form:form commandName="searchVO" id="listForm" name="listForm" method="post">
        <input type="hidden" name="selectedId" />
        <div id="content_pop">
        	<!-- 타이틀 -->
        	<div id="title">
        		<ul>
        			<li><img src="<c:url value='/images/egovframework/example/title_dot.gif'/>" alt=""/><spring:message code="list.sample" /></li>
        		</ul>
        	</div>
        	<!-- // 타이틀 -->
        	<div id="search">
        		<ul>
        			<li>
        			    <%-- <label for="searchCondition" style="visibility:hidden;"><spring:message code="search.choose" /></label> --%>
        				<form:select path="searchCondition" cssClass="use">
        					<form:option value="1" label="전체" />
        					<form:option value="2" label="식대(야근)" />
        					<form:option value="3" label="택시비(야근)" />
        					<form:option value="4" label="택시비(회식)" />
        					<form:option value="5" label="사무용품구매" />
        					<form:option value="6" label="교육비" />
        					<form:option value="7" label="접대비" />
        				</form:select>
        			</li>
         			<li>
        				<form:select path="searchCondition1" cssClass="use">
        					<form:option value="1" label="전체" />
        					<form:option value="2" label="접수" />
        					<form:option value="3" label="승인" />
        					<form:option value="4" label="지급완료" />
        					<form:option value="5" label="반려" />
        				</form:select>
        			</li>
        			<!-- 달력 -->
					 <!-- <input type="text" name="usedate" id="usedate" /> -->
					
					<%-- <form:input path="searchCondition2" id="usedate" /> --%>
					
					<form:input path="searchCondition2" id="usedate" />
					
        			 <%-- <li><label for="searchKeyword" style="visibility:hidden;display:none;">
        			 <spring:message code="search.keyword" /></label>
                        <form:input id="searchKeyword" path="searchKeyword" cssClass="txt"/>
                    </li>
        			<li> --%>
        	            <span class="btn_blue_l">
        	                <a href="javascript:fn_egov_selectList();"><spring:message code="button.search" /></a>
        	                <img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
        	            </span>
        	        </li>
                </ul>
        	</div>

        	<br>총 <c:out value="${totCnt}"/> 건
        	
        	<div id="sysbtn"><!-- 등록 -->
        	  <ul>
        	      <li>
        	          <span class="btn_blue_l">
        	              <a href="javascript:fn_egov_addView();"><spring:message code="button.create" /></a>
                          <img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                      </span>
                  </li>
              </ul>
        	</div>
        	<!-- List -->
        	<div id="table">
        		<table width="1000px" border="0" cellpadding="0" cellspacing="0">
        			<caption style="visibility:hidden"></caption>
        			<colgroup>
        				<%-- <col width="40"/>
        				<col width="100"/>
        				<col width="150"/>
        				<col width="80"/>
        				<col width="?"/>
        				<col width="60"/> --%>
        			</colgroup>
        			<tr>
        				<!-- <th align="center">No</th> -->
        				<%-- <th align="center"><spring:message code="title.sample.id" /></th> --%>
        				<th align="center"><spring:message code="title.sample.usedate" /></th>
        				<th align="center"><spring:message code="title.sample.history" /></th>
        				<th align="center"><spring:message code="title.sample.amount" /></th>
        				<th align="center"><spring:message code="title.sample.authorization" /></th>
        				<th align="center"><spring:message code="title.sample.processing" /></th>
        				<th align="center"><spring:message code="title.sample.registration" /></th>
        			</tr>
        			<c:forEach var="result" items="${resultList}" varStatus="status">
            			<tr>
            				<%-- <td align="center" class="listtd"><c:out value="${result.sno}"/></td> --%>
            				<%-- <td align="center" class="listtd"><c:out value="${paginationInfo.totalRecordCount+1 - ((searchVO.pageIndex-1) * searchVO.pageSize + status.count)}"/></td>
            				<td align="center" class="listtd"><a href="javascript:fn_egov_select('<c:out value="${result.id}"/>')"><c:out value="${result.id}"/></a></td> --%>
            				<td align="center" class="listtd"><c:out value="${result.usedate}"/></td>
            				
            				
            				<%-- <td align="center" class="listtd"><c:out value="${result.history}"/></td> --%>
            				<td align="center" class="listtd"><a href="javascript:fn_egov_select('<c:out value="${result.id}"/>')"><c:out value="${result.history}"/></a></td>
            				

            				<td align="center" class="listtd"><fmt:formatNumber value="${result.amount}" pattern="#,###,###,###" /></td>
            				<td align="center" class="listtd"><fmt:formatNumber value="${result.authorization}" pattern="#,###,###,###" /></td>
            				<td align="center" class="listtd"><c:out value="${result.processing}"/></td>
            				<td align="center" class="listtd"><c:out value="${result.registration}"/></td>
            			</tr>
            			<c:set var="atotal" value="${atotal + result.amount}"/>
            			<c:set var="btotal" value="${btotal + result.authorization}"/>
        			</c:forEach>
            			<tr>
            				<td align="center" class="listtd"></td>
            				<td align="center" class="listtd"></td>
            				<td align="center" class="listtd"><fmt:formatNumber value="${atotal}" pattern="#,###,###,###" /></td>
            				<td align="center" class="listtd"><fmt:formatNumber value="${btotal}" pattern="#,###,###,###" /></td>
            				<td align="center" class="listtd"></td>
            				<td align="center" class="listtd"></td>
            			</tr>
        		</table>
        	</div>
        	<!-- /List -->
<%--         	<div id="paging">
        		<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="fn_egov_link_page" />
        		<form:hidden path="pageIndex" />
        	</div> --%>
        </div>
    </form:form>
    
    <!-- 엑셀 다운로드 -->
  <form id="excelForm" name="excelForm" method="post" action="excelDown.do">
    <input type="text" name="fileName" />
    <input type="submit" value="엑셀다운로드" />
  </form>
  
  <!-- 엑셀 업로드 -->
  <form id="excelUploadForm" name="excelUploadForm" enctype="multipart/form-data"
    method="post" action= "excelUploadAjax.do">
    <div class="contents">
    <div>첨부파일은 한개만 등록 가능합니다.</div>

    <dl class="vm_name">
      <dt class="down w90">첨부 파일</dt>
        <dd><input id="excelFile" type="file" name="excelFile" /></dd>
      </dl>        
    </div>

    <div class="bottom">
      <button type="button" id="addExcelImpoartBtn" class="btn" onclick="check()" ><span>추가</span></button>
    </div>
  </form>




</body>
</html>