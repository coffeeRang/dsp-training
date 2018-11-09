<%@page import="sy.training3.day1108.Menu"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MenuTable</title>
			<% 
			Menu mu = new Menu();
			
			%>
</head>
<body>
	<table border="1" width="80%">
		<%
			out.println(mu.tableParser());
		%>
		
	</table>
</body>
</html>