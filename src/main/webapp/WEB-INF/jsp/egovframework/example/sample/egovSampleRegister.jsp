<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"      uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="spring"    uri="http://www.springframework.org/tags"%>
<%
  /**
  * @Class Name : egovSampleRegister.jsp
  * @Description : Sample Register 화면
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
<script src="https://code.jquery.com/jquery-1.12.4.js"></script><!-- 달력 -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <c:set var="registerFlag" value="${0 == sampleVO.id ? 'create' : 'modify'}"/>
    <title>Sample <c:if test="${registerFlag == 'create'}"><spring:message code="button.create" /></c:if>
                  <c:if test="${registerFlag == 'modify'}"><spring:message code="button.modify" /></c:if>
    </title>
    <link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/sample.css'/>"/>
    
    <!--For Commons Validator Client Side-->
    <script type="text/javascript" src="<c:url value='/cmmn/validator.do'/>"></script>
    <validator:javascript formName="sampleVO" staticJavascript="false" xhtml="true" cdata="false"/>
    
    <script type="text/javaScript" language="javascript" defer="defer">
        <!--
        /* 글 목록 화면 function */
        function fn_egov_selectList() {
           	document.detailForm.action = "<c:url value='/egovSampleList.do'/>";
           	document.detailForm.submit();
        }
        
        /* 글 삭제 function */
        function fn_egov_delete() {
           	document.detailForm.action = "<c:url value='/deleteSample.do'/>";
           	document.detailForm.submit();
        }
        
        /* 글 등록 function */
        function fn_egov_save() {
        	frm = document.detailForm;
        	if(!validateSampleVO(frm)){
        		return;
        	}else{
        		frm.action = "<c:url value="${registerFlag == 'create' ? '/addSample.do' : '/updateSample.do'}"/>";
        		frm.submit();
        	}
        }
        
        -->

        $(function(){
            $("#usedate").datepicker({
            	dateFormat: 'yy-mm-dd' 
            });
        });
    </script>
    <style>
    #remarks {
    	width:150px;
    	height:50px;
    }
    </style>
</head>
<body style="text-align:center; margin:0 auto; display:inline; padding-top:100px;">
<form:form commandName="sampleVO" id="detailForm" name="detailForm" enctype="multipart/form-data" method="post">
    <div id="content_pop">
    	<!-- 타이틀 -->
    	<div id="title">
    		<ul>
    			<li><img src="<c:url value='/images/egovframework/example/title_dot.gif'/>" alt=""/>
                    <c:if test="${registerFlag == 'create'}"><spring:message code="button.create" /></c:if>
                    <c:if test="${registerFlag == 'modify'}"><spring:message code="button.modify" /></c:if>
                </li>
    		</ul>
    	</div>
    	<!-- // 타이틀 -->
    	<div id="table">
    	<table width="100%" border="1" cellpadding="0" cellspacing="0" style="bordercolor:#D3E2EC; bordercolordark:#FFFFFF; BORDER-TOP:#C2D0DB 2px solid; BORDER-LEFT:#ffffff 1px solid; BORDER-RIGHT:#ffffff 1px solid; BORDER-BOTTOM:#C2D0DB 1px solid; border-collapse: collapse;">
    	<h2>청구내역</h2>
    		<colgroup>
    			<col width="150"/>
    			<col width="?"/>
    		</colgroup>
<%--     		<c:if test="${registerFlag == 'modify'}"> 
        		<tr><!--기존에 있던 아이디-->
        			<td class="tbtd_caption">
        			<label for="id"><spring:message code="title.sample.id" /></label></td>
        			<td class="tbtd_content">
        			</td>
        		</tr>
    		</c:if> --%>
      				 <form:input type="hidden" path="id" cssClass="essentiality" maxlength="10" readonly="true" /> 
<%--     		<tr><!-- 기존에 있던 이름 카데고리명 -->
    			<td class="tbtd_caption"><label for="name"><spring:message code="title.sample.name" /></label></td>
    			<td class="tbtd_content">
    				<form:input path="name" maxlength="30" cssClass="txt"/>
    				&nbsp;<form:errors path="name" />
    			</td>
    		</tr> --%>
    		<tr><!-- 사용내역 -->
    			<td class="tbtd_caption"><label for="history"><spring:message code="title.sample.history" /></label></td>
    			<td class="tbtd_content">
    				<form:select path="history" cssClass="use">
    					<form:option value="식대(야근)" label="식대(야근)" />
    					<form:option value="택시비(야근)" label="택시비(야근)" />
    					<form:option value="택시비(회식)" label="택시비(회식)" />
    					<form:option value="사무용품구매" label="사무용품구매" />
    					<form:option value="교육비" label="교육비" />
    					<form:option value="접대비" label="접대비" />
    				</form:select>
    			</td>
    		</tr>
   			<tr><!-- 사용일 입력 -->
    			<td class="tbtd_caption">
    			<label for="usedate"><spring:message code="title.sample.usedate" /></label></td>
    			<td class="tbtd_content">
    			 <c:if test="${registerFlag == 'modify'}">
    				<form:input path="usedate" maxlength="8" cssClass="essentiality" readonly="true" size="30"/>
    				<!-- <input type="text" name="usedate" id="usedate" size="12" /> -->
   				</c:if>
   				<c:if test="${registerFlag != 'modify'}">
    				<!-- <input type="text" name="usedate" id="usedate" size="12" /> -->
    				<form:input path="usedate" maxlength="8" cssClass="txt"/>
   				</c:if>
    			</td>
    		</tr>
  			<tr><!-- 사용금액 입력 -->
    			<td class="tbtd_caption"><label for="amount"><spring:message code="title.sample.amount" /></label></td>
    			<td class="tbtd_content">
    			<c:if test="${registerFlag == 'modify'}">
    				<form:input path="amount" maxlength="10" cssClass="essentiality" readonly="true"/>
   				</c:if>
   				<c:if test="${registerFlag != 'modify'}">
    				<form:input path="amount" maxlength="10" cssClass="txt"/>
    				<form:input type="hidden" path="processing" cssClass="txt" value="접수"/>
   				</c:if>
    			</td>
    		</tr>
   			<!-- 영수증 사진 -->
    		<tr>
    			<td class="tbtd_caption">
    			<label for="fileName"><spring:message code="title.sample.fileName" /></label></td>
    			<td class="tbtd_content">
    			<c:if test="${registerFlag == 'modify'}">
    				<img src="/sapie/upload/<c:out value='${sampleVO.fileName}'/>" width="50%"/>
    			</c:if>
    			<c:if test="${registerFlag != 'modify'}">
    				<input type="file" name="files"/>
    			</c:if>
    			</td>
    		</tr>
    		
<%--     		<tr><!-- 기존에 있던 사용함 사용안함 셀렉트박스 -->
    			<td class="tbtd_caption"><label for="useYn"><spring:message code="title.sample.useYn" /></label></td>
    			<td class="tbtd_content">
    				<form:select path="useYn" cssClass="use">
    					<form:option value="Y" label="Yes" />
    					<form:option value="N" label="No" />
    				</form:select>
    			</td>
    		</tr> --%>
<%--     		<tr><!-- 기존에 있던 설명 -->
    			<td class="tbtd_caption"><label for="description"><spring:message code="title.sample.description" /></label></td>
    			<td class="tbtd_content">
    				<form:textarea path="description" rows="5" cols="58" />&nbsp;<form:errors path="description" />
                </td>
    		</tr> --%>
<%--     		<tr><!-- 기존에 있던 등록자 -->
    			<td class="tbtd_caption"><label for="regUser"><spring:message code="title.sample.regUser" /></label></td>
    			<td class="tbtd_content">
                    <c:if test="${registerFlag == 'modify'}">
        				<form:input path="regUser" maxlength="10" cssClass="essentiality" readonly="true" />
        				&nbsp;<form:errors path="regUser" /></td>
                    </c:if>
                    <c:if test="${registerFlag != 'modify'}">
        				<form:input path="regUser" maxlength="10" cssClass="txt"  />
        				&nbsp;<form:errors path="regUser" /></td>
                    </c:if>
    		</tr> --%>
    		
    	</table>



<c:if test="${registerFlag == 'modify'}"><!-- 수정페이지 이면 -->
    	<h2>처리내역</h2>
    	<table width="100%" border="1" cellpadding="0" cellspacing="0" style="bordercolor:#D3E2EC; bordercolordark:#FFFFFF; BORDER-TOP:#C2D0DB 2px solid; BORDER-LEFT:#ffffff 1px solid; BORDER-RIGHT:#ffffff 1px solid; BORDER-BOTTOM:#C2D0DB 1px solid; border-collapse: collapse;">
    		<colgroup>
    			<col width="150"/>
    			<col width="?"/>
    		</colgroup>
    		<tr><!-- 처리상태 -->
    			<td class="tbtd_caption">
    			<label for="processing"><spring:message code="title.sample.processing" /></label></td>
    			<td class="tbtd_content">
   				<c:if test="${registerFlag == 'modify'}">
   				<form:select path="processing" cssClass="use">
    					<form:option value="접수" label="접수" />
    					<form:option value="승인" label="승인" />
    					<form:option value="지급완료" label="지급완료" />
    					<form:option value="반려" label="반려" />
    				</form:select>
   				</c:if>
    			</td>
    		</tr>
    		<c:set var="processing" value="승인" scope="session"/>
			    <c:if test="${processing eq '승인'}">
			    </c:if>
    		
    		
    		<tr><!-- 처리일시 -->
    			<td class="tbtd_caption">
    			<label for="processingdate"><spring:message code="title.sample.processingdate" /></label></td>
    			<td class="tbtd_content">
    					<form:input path="processingdate" maxlength="8" cssClass="txt"/>   				
    			</td>
    		</tr>
    		<tr><!-- 승인금액 -->
    			<td class="tbtd_caption">
    			<label for="authorization"><spring:message code="title.sample.authorization" /></label></td>
    			<td class="tbtd_content">
    				<form:input path="authorization" maxlength="10" cssClass="txt"/>
    			</td>
    		</tr>
    		<tr><!-- 비고 -->
    			<td class="tbtd_caption">
    			<label for="remarks"><spring:message code="title.sample.remarks" /></label></td>
    			<td class="tbtd_content">
    				<form:textarea path="remarks"  cssClass="txt"/>
    			</td>
    		</tr>
   		</table>
 </c:if>
 <c:if test="${registerFlag != 'modify'}">
 </c:if>
   		
      </div>
    	<div id="sysbtn">
    		<ul>
    			<li>
                    <span class="btn_blue_l"><!-- 목록 -->
                        <a href="javascript:fn_egov_selectList();"><spring:message code="button.list" /></a>
                        <img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                    </span>
                </li>
                <c:if test="${registerFlag != 'modify'}">
    			<li><!-- 등록 페이지 에서 보이게 만듬 -->
                    <span class="btn_blue_l">
                        <a href="javascript:fn_egov_save();">
                            <c:if test="${registerFlag == 'create'}"><spring:message code="button.create1" /></c:if>
                        </a>
                        <img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                    </span>
                </li>
                </c:if>
                
                
                <c:if test="${registerFlag == 'modify'}">
                <li><!-- 수정페이지 라면 승인이 보이게 만든다. -->
                    <span class="btn_blue_l">
                        <a href="javascript:fn_egov_save();">
                            <c:if test="${registerFlag == 'modify'}"><spring:message code="button.aproved" /></c:if>
                        </a>
                        <img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                    </span>
                </li>
                </c:if>
                
                <c:if test="${registerFlage == 'modify'}"><!-- 승인이라면.. 수정못하게 -->
                	
                </c:if>
                
                
    			<c:if test="${registerFlag == 'modify'}">
                    <li><!-- 삭제 -->
                        <span class="btn_blue_l">
                            <a href="javascript:fn_egov_delete();"><spring:message code="button.delete" /></a>
                            <img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                        </span>
                    </li>
    			</c:if>
    			<li><!-- 재설정 -->
                    <span class="btn_blue_l">
                        <a href="javascript:document.detailForm.reset();"><spring:message code="button.reset" /></a>
                        <img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                    </span>
                </li>
            </ul>
    	</div>
    </div>
    <!-- 검색조건 유지 -->
    <input type="hidden" name="searchCondition" value="<c:out value='${searchVO.searchCondition}'/>"/>
    <input type="hidden" name="searchKeyword" value="<c:out value='${searchVO.searchKeyword}'/>"/>
    <input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>"/>
</form:form>
</body>
</html>