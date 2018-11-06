<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.util.*"%>
<%@page import="sy.training3.day1106.FinTable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FinTable</title>
			<% 
			FinTable ft = new FinTable();
			
			%>
</head>
<body>
	<table border="1" width="80%">
		<tr style="text-align: center;">
			<td>계정구분</td>
			<td>조직</td>
			<td>사업부문</td>
			<td>계획</td>
			<td>추정</td>
			<td>현황</td>
			<td>특이사항</td>
			<td>단위</td>
		</tr>
		
		<%
			out.print(ft.tableParser());
		
		%>
		
	</table>
</body>
</html>