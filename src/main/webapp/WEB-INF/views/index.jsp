<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>BRAT 작업결과 검수작업 보조 도구</title>

        <link href="resources/assets/css/animate.css" rel="stylesheet" media="screen">
        <link rel="stylesheet" href="resources/assets/css/bootstrap-select.min.css"> 
        <link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="resources/assets/css/style.css">
        <link rel="stylesheet" href="resources/assets/css/responsive.css">
    </head>
    <body>
    <div class="page-head"> 
        <div class="container">
            <div class="row">
                <div class="page-head-content">
                    <h1 class="page-title">BRAT 작업결과 검수작업 보조 도구</h1>               
                </div>
            </div>
        </div>
    </div>
    <!-- End page header -->

    <!-- register-area -->
    <div class="register-area">
        <div class="container">
            <div class="col-md-6 col-md-offset-3">
                <div class="box-for overflow">                         
                    <div class="col-md-12 col-xs-12 login-blocks">
                        <h2>Login : </h2> 
                        <form action="/brats/account/login.action" method="post">
                            <div class="form-group">
                                <label for="id">ID</label>
                                <input type="text" class="form-control" name="id">
                            </div>
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" class="form-control"  name="password">
                            </div>
                            <div class="text-center">
                                <button type="submit" class="btn btn-default"> Log in</button>
                            </div>
                        </form>
                		</div>
             	</div>
			</div>
        </div>
    </div>      
    </body>
</html>