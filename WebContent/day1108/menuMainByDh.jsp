<%@page import="dh.training3.day1108.Menu"%>
<%@page import="dh.util.TrainingUtil"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.List"%>
<%@page import="dh.training3.day1101.AccountSumrByJson"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="dh.util.UseJson"%>
<%@page import="dh.training3.day1101.AccountSumrByMap"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TrainingUtil util = new TrainingUtil();
	Object obj = util.getFile("menu_db.json");
	JSONArray jsonArr = null;
	
	if (obj != null) {
		jsonArr = (JSONArray)obj;
	}
	
	Menu menu = new Menu();
	List<Map<String, Object>> list = menu.replaceFormat(jsonArr);

%>
<!DOCTYPE html>
<html>
<style>
  table {
    width: 100%;
    border: 1px solid #444444;
    border-collapse: collapse;
  }
  th, td {
    border: 1px solid #444444;
  }
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>메뉴구조도</title>
</head>
<body>
	<center><h1>계정집계 테이블</h1></center>
	<table border="1" style="width:90%; margin: 0 auto;">
		<%
			out.print(menu.makeTable(list));
		%>
		
		
		
	</table>
</body>
</html>