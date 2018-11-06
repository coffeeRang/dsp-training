<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="dh.util.UseJson"%>
<%@page import="dh.training3.day1101.AccountSumr"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
UseJson useJson = new UseJson();
JSONObject jsonObj =  useJson.getJsonFile();
JSONArray jsonArr = (JSONArray)jsonObj.get("accountObj");

AccountSumr sumr = new AccountSumr(); /* */


%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>계정집계</title>
</head>
<body>
	<center><h1>계정집계 테이블</h1></center>
	<table border="1" style="width:90%; margin: 0 auto;">
		<tr class="head">
			<td style="text-align: center;">계정구분</td>
			<td style="text-align: center;">조직</td>
			<td style="text-align: center;">사업부문</td>
			<td style="text-align: center;">계획</td>
			<td style="text-align: center;">추정</td>
			<td style="text-align: center;">현황</td>
			<td style="text-align: center;">특이사항</td>
			<td style="text-align: center;">단위</td>
		</tr>
		<%
			out.print(sumr.testReplaceFormat(jsonArr));
		%>
		
		
		
	</table>
</body>
</html>