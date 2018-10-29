<%@page import="java.util.*"%>
<%@page import="sy.training2.day1029_3.BsTable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	BsTable bt = new BsTable();
	HashMap<String, Object> jsonO = (HashMap<String, Object>) bt.get();
%>

</head>
<body>
	<table border='1' width="100%">
		<tr>
			<th style="text-align: left; background-color: gray;">펼치기</th>
			<th style="background-color: gray;">당기</th>
			<th style="background-color: gray;">전기</th>
			<th style="background-color: gray;">전년동기</th>
		</tr>
		<%
			for (String key : jsonO.keySet()) {
				HashMap<String, Object> map1 = (HashMap<String, Object>) jsonO.get(key);
				
		%>
		<tr>
			<!-- 자산 부채 -->
			<th style="text-align: left"><%=map1.get("account_sbjt_name")%></th>
			<td>당기</td>
			<td>전기</td>
			<td>전년동기</td>
		</tr>
			<%
				for(String key2 : map1.keySet()) {
					//HashMap<String,Object> map2 = (HashMap<String, Object>) map1.get(key2);
					System.out.println(map1.get("02"));
					System.out.println(map1.get("01"));
					System.out.println(map1.get("16"));
			%>

		<%
			}
		}
		%>
	</table>
</body>
</html>