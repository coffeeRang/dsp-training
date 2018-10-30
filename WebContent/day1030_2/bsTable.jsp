<%@page import="java.util.*"%>
<%@page import="sy.training2.day1030_2.BsTable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bsTable</title>
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
			/* stdMap을 가짐 */
			for (String key : jsonO.keySet()) {
				if(!key.equals("F240000")){
				HashMap<String, Object> map1 = (HashMap<String,Object>) jsonO.get(key);
				/* System.out.println(">>>1"+map1); */
		%>
		<tr>
			<!-- 자산+합계 , 부채+합계 -->
			<th><%=map1.get("account_sbjt_name")%></th>
			<td style="text-align: right"><%=map1.get("02")%></td>
			<td style="text-align: right"><%=map1.get("01")%></td>
			<td style="text-align: right"><%=map1.get("16")%></td>
		</tr>
		
			<% 
				/* prevMap을 가짐 */
				for (String key2 : map1.keySet()) {
						if (!key2.equals("account_sbjt_name")&& !key2.equals("01")&& !key2.equals("02")&& !key2.equals("16")){
							HashMap<String, Object> map2 = (HashMap<String, Object>) map1.get(key2);
							/* System.out.println(">>>2"+map2); */
			%>
			<tr><!-- 유동자산 , 비유동자산 -->
			<th><%= map2.get("std_name")%></th>
			<td style="text-align: right"><%=map2.get("02")%></td>
			<td style="text-align: right" ><%=map2.get("01")%></td>
			<td style="text-align: right"><%=map2.get("16")%></td>
			</tr>
			<% 
				/* seriesMap을 가짐 */
				for (String key3 : map2.keySet()) {
						if (!key3.equals("std_name")&& !key3.equals("01")&& !key3.equals("02")&& !key3.equals("16")){
							HashMap<String, Object> map3 = (HashMap<String, Object>) map2.get(key3);
							/* System.out.println(">>>>>3"+map3); */
			%>
			<!-- 자손 -->
			<tr>
			<td style="text-align: left"><%=map3.get("std_name")%></td>
			<td style="text-align: right" ><%=map3.get("02")%></td>
			<td style="text-align: right"><%=map3.get("01")%></td>
			<td style="text-align: right"><%=map3.get("16")%></td>
			</tr>
		<%
							}
						}	
					}
				}
			}
		}
		%>
		
		<%
			/* stdMap을 가짐 */
			for (String key : jsonO.keySet()){
			if(key.equals("F240000")){
			HashMap<String, Object> map1 = (HashMap<String,Object>) jsonO.get(key);
		%>
		<tr>
		<th><%=map1.get("account_sbjt_name") %></th>
		<td style="text-align: right"><%=map1.get("02") %></td>
		<td style="text-align: right"><%=map1.get("01") %></td>
		<td style="text-align: right"><%=map1.get("16") %></td>
		</tr>
		<%
			/* prevMap을 가짐 */
			for (String key2 : map1.keySet()) {
				if (!key2.equals("account_sbjt_name")&& !key2.equals("std_cd")&& !key2.equals("01")&& !key2.equals("02")&& !key2.equals("16")){
					HashMap<String, Object> map2 = (HashMap<String, Object>) map1.get(key2);
					
		%>
		<tr>
			<th style="text-align: left"><%=map2.get("std_name")%></th>
			<td style="text-align: right"><%=map2.get("02")%></td>
			<td style="text-align: right"><%=map2.get("01")%></td>
			<td style="text-align: right"><%=map2.get("16")%></td>
		</tr>
		
		<%
				}
			}
		%>
		
		<%		
			}
		}
		%>
	</table>
</body>
</html>