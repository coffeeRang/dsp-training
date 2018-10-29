<%@page import="java.util.*"%>
<%@page import="sy.training1.day1029.BsTable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
			/* 매출액을 더한것과 매출원가를 더한액수, 매출총이익을 더한액수가 들어간다. */
			for (String key : jsonO.keySet()) {
				HashMap<String, Object> map1 = (HashMap<String, Object>) jsonO.get(key);
				
				
		%>
		<tr>
			<!-- 매출액 , 매출원가, 매출총이익 -->
			<th style="text-align: left"><%=map1.get("account_sbjt_name")%></th>
			<td style="text-align: right"><%=map1.get("02") %></td>
			<td style="text-align: right"><%=map1.get("01") %></td>
			<td style="text-align: right"><%=map1.get("16") %></td>
		</tr>
			<!-- std_cd를 담고있는 맵-->
			<%
				for (String key2 : map1.keySet()) {
						if (!key2.equals("account_sbjt_name")&& !key2.equals("01")&& !key2.equals("02")&& !key2.equals("16")){
							HashMap<String, Object> map2 = (HashMap<String, Object>) map1.get(key2);
			%>
			<tr>
			<td><%= map2.get("std_name")%></td>
			
			<!-- 02, 01, 16을 담고있는 맵 -->
			<% 
			Object ser1 = null;
			Object ser2 = null;
			Object ser3 = null;
			
			/* 당기,전기,전년동기의 seriesdata를 각각 넣어서 자리에맞게 나눠준다. */
			for (String key3 : map2.keySet()) {
				if (!key3.equals("std_name")) {
					HashMap<String, Object> map3 = (HashMap<String, Object>) map2.get(key3);
					/* 당기 */
					if(key3.equals("02")){ ser1 = map3.get("seriesdata") ;}
						/* 전기 */
					if(key3.equals("01")){ ser2 = map3.get("seriesdata") ;}
							/* 전년동기 */
					if(key3.equals("16")){ ser3 = map3.get("seriesdata") ;}
				}
			}
			%>
			<td style="text-align: right"><%= ser1 %></td>
			
			<td style="text-align: right"><%= ser2 %></td>
			
			<td style="text-align: right"><%= ser3 %></td>
			
			<%
					}
				}
			%>
		</tr>
		<%}%>
	</table>
</body>
</html>