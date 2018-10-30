<%@page import="gun.training2.day1030.JsonSimpleOneRow"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>json 테스트 페이지</title>
<style>
table {
	border-collapse: collapse;
	border: solid black 2px;
}

table td {
	padding: 25px;
}
</style>
</head>
<body>
	<%
		JsonSimpleOneRow jsonSimple = new JsonSimpleOneRow();
		out.println(jsonSimple.getList());
	%>
</body>
</html>