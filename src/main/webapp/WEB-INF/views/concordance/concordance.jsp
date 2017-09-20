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
                     <!-- text/event-type, 연결속성, entity/entity-type/mentionType/Relation-type(all/coref), type1,2(pgen,dis/pgen,dis) 에 대한 검색 -->        
					<h3>검색 ( 전체 파일 )</h3><br><br>
                    	<div class="search-form wow pulse" data-wow-delay="0.8s">
                    		<ul class="nav nav-tabs" style="font-size: 23">
	    					<li class="active"><a data-toggle="tab" href="#Text">Text</a></li>
				    		<li><a data-toggle="tab" id="entityTab" href="#Entity">Entity</a></li>
	    					<li><a data-toggle="tab" id="eventTab" href="#Event">Event</a></li>
	 					<li><a data-toggle="tab" id="relationTab" href="#Relation">Relation</a></li>
	    				</ul>
	    				<br><br>
                    <!-- event-type, 연결속성, entity/entity-type/mentionType/Relation-type(all/coref), type1,2(pgen,dis/pgen,dis) 에 대한 검색 -->        
	    				<div class="tab-content">
	    					<div id="Text" class="tab-pane fade in active">
	    						<form action="" class=" form-inline">
                        			<div class="form-group" style="width:500;">
                                 	<input type="text" id="textKeyword" class="form-control" placeholder="Key word ( empty = anything)">
                            		</div>
                             	<button id="textSearch" class="btn search-btn" type="button"><i class="fa fa-search"></i></button>
                    			</form>
	    					</div>
	    					<div id="Entity" class="tab-pane fade">
	    						<form action="" class=" form-inline">
	                             <div class="form-group">                           
	                             <!-- text/event-type, 연결속성, entity/entity-type/mentionType/Relation-type(all/coref), type1,2(pgen,dis/pgen,dis) 에 대한 검색 -->        
	                             	<select id="entityType" class="selectpicker"  title="Type" style="width:auto;">
	                                 	<option value="">-Any-</option>
	                                 	<option value="PER">PERSON</option>
	                                 	<option value="ORG">ORGANIZATION</option>
	                                 	<option value="LOC">LOCATION</option>
	                                 	<option value="GPE">GEO-POLITICAL ENTITY</option>
	                                 	<option value="FAC">FACILITY</option>
	                                 	<option value="DIS">DISEASE</option>
	                                 	<option value="PGEN">PATHOGEN</option>
	                                 	<option value="TIME">TIME</option>
	                                 	<option value="NPER">NumOfPerson</option>
	                                 	<option value="SYMP">Symptom</option>
	                                 </select>
	                             </div>
	                        		<div class="form-group">
	                        			<input type="text" id="entityKeyword" class="form-control" placeholder="Key word ( empty = anything)">
	                             </div>
                             	<button id="entitySearch" class="btn search-btn" type="button"><i class="fa fa-search"></i></button>
	                    		</form>
	    					</div>
	    					<div id="Event" class="tab-pane fade">
	    						<form action="" class=" form-inline">
	                             <div class="form-group">                           
	                             <!-- text/event-type, 연결속성, entity/entity-type/mentionType/Relation-type(all/coref), type1,2(pgen,dis/pgen,dis) 에 대한 검색 -->        
	                             	<select id="evnetType" class="selectpicker" data-live-search="true" data-live-search-style="begins" title="Type">
	                                 	<option value="">-Any-</option>
	                                 	<option value="Suspect">Suspect</option>
	                                     <option value="Infect">Infect</option>
	                                     <option value="Kill">Kill</option>
	                                     <option value="Announce">Announce</option>
	                                 </select>
	                             </div>
	                        		<div class="form-group">
	                        			<input type="text" id="eventKeyword" class="form-control" placeholder="Key word ( empty = anything)">
	                             </div>
                             	<button id="eventSearch" class="btn search-btn" type="button"><i class="fa fa-search"></i></button>
	                    		</form>
	    					</div>
	    				</div>   
	    				<br>	
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
					            <h2 class="s-property-title">검색 결과</h2>
					            <br>
								<table class="table">
								    <thead>
								      	<tr class="warning">
								    	    		<th>문서명</th>
								       		<th>Line</th>
								        		<th>해당 문장</th>
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
        
        <script src="../resources/assets/brats_js/concordance.js"></script>
    </body>
</html>