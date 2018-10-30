<%@page import="gun.training2.day1030.JsonSimpleSaleOneRow"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>json 테스트 페이지</title>
<style>
table {
	border-collapse: collapse;
}

table td, th{
	padding: 15px;
}
table td{
	text-align:right;
}

.title {
	background-color: lightgrey;
}

.title th {
	height: 35px;
}
</style>
</head>
<body>
<%
	JsonSimpleSaleOneRow jsonSimple = new JsonSimpleSaleOneRow();
	out.println(jsonSimple.getJson());
%>
</body>
</html>