<%@page import="java.util.ArrayList"%>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>Brat Assist Home</title>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    
    <meta name="description" content="GARO is a real-estate template">
    <meta name="author" content="Kimarotec">
    <meta name="keyword" content="html5, css, bootstrap, property, real-estate theme , bootstrap template">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- jQuery Version 2.1.3 -->
    <script src="../resources/assets/js/jquery-1.10.2.min.js"></script>

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link rel="icon" href="favicon.ico" type="image/x-icon">

	<link rel="stylesheet" href="../resources/assets/css/normalize.css">
    <link rel="stylesheet" href="../resources/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="../resources/assets/css/fontello.css">
    <link href="../resources/assets/fonts/icon-7-stroke/css/pe-icon-7-stroke.css" rel="stylesheet">
    <link href="../resources/assets/fonts/icon-7-stroke/css/helper.css" rel="stylesheet">
    <link href="../resources/assets/css/animate.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="../resources/assets/css/bootstrap-select.min.css"> 
    <link rel="stylesheet" href="../resources/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="../resources/assets/css/icheck.min_all.css">
    <link rel="stylesheet" href="../resources/assets/css/price-range.css">
    <link rel="stylesheet" href="../resources/assets/css/owl.carousel.css">  
    <link rel="stylesheet" href="../resources/assets/css/owl.theme.css">
    <link rel="stylesheet4" href="../resources/assets/css/owl.transitions.css">
    <link rel="stylesheet" href="../resources/assets/css/style.css">
    <link rel="stylesheet" href="../resources/assets/css/responsive.css">
</head>
<body>
	<div id="preloader">
        <div id="status">&nbsp;</div>
    </div>
    
    <!--  header  -->
    <c:import url="/WEB-INF/views/include/header.jsp" />
    
    <!-- Body content -->
    <!-- folder 경로 설정 -->
    <div class="testimonial-area recent-property">
		<div class="container">
			<div class="row">
				<div class="col-md-10 col-md-offset-1 col-sm-12 text-center page-title">
                    	<div class="search-form wow pulse" data-wow-delay="0.8s">
                     	<c:choose>
                        		<c:when test="${ sessionScope.userType == 1 }">
                    				<h4>MentionType 검사 대상 폴더 선택 ( IIRTECH )</h4>
                    				<br>
                    				<form class=" form-inline">
		                     		<div class="form-group"  style="width:100;" >
		                     			<select id="type" class="selectpicker" data-live-search="true" data-live-search-style="begins"  title="검사 유헝" >
		                     				<option value="missing">누락</option>
		                     				<option value="added">미삭제</option>
		                     			</select>
		                     		</div>
		                     		<div class="form-group">
		                     			<select id="weeks" class="selectpicker" data-live-search="true" data-live-search-style="begins"  title="작업 주차" >
		                     				<c:forEach var="folder" items="${ folderList }">
		                     					<option value="${ folder }"> ${ folder } </option>
		                     				</c:forEach>
		                     			</select>
		                     		</div>
			                        	<div class="form-group" id ="job">                                   
			                        		<select id="step" class="selectpicker" title="작업 단계 (Defaut : 3rd)" data-live-search="true" data-live-search-style="begins" >
			                             </select>
			                         </div>
			                         <button id="mentionTypeCheck" class="btn search-btn" type="button"><i class="fa fa-search"></i></button>
			                		</form>
			                		<br>
               				</c:when>
               				<c:otherwise>
                				</c:otherwise>
                        	</c:choose>
                		</div>
             	</div>
        		</div>
           	<br><br><br>
		</div>
	</div>
	
    <div class="testimonial-area recent-property" style="background-color: #FCFCFC; padding-bottom: 15px;">
        <div class="container">
            <div class="clearfix padding-top-40">
                <div class="col-md-10 col-md-offset-1 col-sm-12 single-property-content " style="background-color: #FCFCFC; padding-bottom: 15px;">
					<div class="single-property-wrapper">
					    <div class="section additional-details">
					            <h2 class="s-property-title">검사 결과</h2>
					            <br>
								<table class="table">
								    <thead>
								      	<tr class="warning">
								    	    		<th>문서명</th>
								       		<th>Line</th>
								        		<th>속성명</th>
								        		<th>단어</th>
								        		<th>이동</th>
								      	</tr>
								    </thead>
								    <tbody id="contents">
								    </tbody>
								</table>
					        </div> 
					    </div> 
				    </div>
			    </div>
		    </div>
	    </div>
	    <br><br><br>

        <script src="../resources/bootstrap/js/bootstrap.min.js"></script>
        <script src="../resources/assets/js/bootstrap-select.min.js"></script>
        <script src="../resources/assets/js/bootstrap-hover-dropdown.js"></script>

        <script src="../resources/assets/js/easypiechart.min.js"></script>
        <script src="../resources/assets/js/jquery.easypiechart.min.js"></script>

        <script src="../resources/assets/js/owl.carousel.min.js"></script>
        <script src="../resources/assets/js/wow.js"></script>

        <script src="../resources/assets/js/icheck.min.js"></script>
        <script src="../resources/assets/js/price-range.js"></script>

        <script src="../resources/assets/js/main.js"></script>
        
        <script src="../resources/assets/brats_js/mentionType.js"></script>
    </body>
</html>