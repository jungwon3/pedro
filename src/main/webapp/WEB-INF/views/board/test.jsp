<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.*"%>
<%
	//파일 기본경로
	String defaultPath = request.getSession().getServletContext().getRealPath("/");
	//파일 기본경로, 상세경로
	String filePath = defaultPath + "ckEimg" + File.separator;
%>
<html>
<head>
	<title>test</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="/resources/ckeditor/ckeditor.js"></script>
	<script>
    window.onload = function(){
       ck = CKEDITOR.replace("editor");
    };
    </script>
    
    
</head>
<body>
<h1>editor check</h1>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<textarea name="content" id="editor"></textarea>
	
	<input type="button" value="등록하기">









</body>
	
</html>