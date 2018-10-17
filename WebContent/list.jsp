<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="gun.training1.day1017.TestMain"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Map<String, Object> jsonMap = (HashMap<String, Object>) TestMain.getJson();
	Map<String, Object> comNumMap = null;
	//positionName, evalFieldCd를 key로 갖는 map
	Map<String, Object> posCdMap = null;
	//evalName, list를 key로 갖는 map
	Map<String, Object> evalCdMap = null;
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>json 테스트 페이지</title>
</head>
<body>
	<%
		for (String companyNum : jsonMap.keySet()) {
			comNumMap = (HashMap<String, Object>) jsonMap.get(companyNum);
			String companyName = (String) comNumMap.get("companyName");
	%>
	<h2><%=companyName%></h2>
	<hr style="border: solid 1px black;">
	<table border="1"
		style="width: 100%; text-align: center; border-collapse: collapse;">
		<tr>
			<th style="width: 20%;" rowspan="2">직책</th>
			<th style="width: 80%;" colspan="2">KPI</th>
		</tr>
		<tr>
			<th style="width: 20%;">구분</th>
			<th style="width: 80%;">성과지표</th>
		</tr>
		<%
			for (String positionCd : comNumMap.keySet()) {
		%>
		<tr>
			<%
				if (!positionCd.equals("companyName")) {
							posCdMap = (HashMap<String, Object>) comNumMap.get(positionCd);
							String positionName = (String) posCdMap.get("positionName");
			%>
			<td><%=positionName%></td>

			<%
				//for (Object evalFieldCd : posCdMap.keySet()) {
							//if (!evalFieldCd.equals("positionName")) {
							evalCdMap = (HashMap<String, Object>) posCdMap.get("HE01");
							//System.out.println(evalCdMap);
							String evalFieldName = (String) evalCdMap.get("evalFieldName");
							List<String> list = (ArrayList<String>) evalCdMap.get("list");
			%>
			<td><%=evalFieldName%></td>
			<td
				style="text-align: left; padding-left: 30px; padding-top: 15px; padding-bottom: 15px;">
				<%--==========<br/>
			<%=evalCdMap.get("evalFieldName")%><br/>
			==========<br/>
			<%=comNumMap.get("companyName")%><br/>
			==========<br/>
			<%=posCdMap.get("positionName")%><br/>
			==========<br/>
			<%=evalCdMap.get("evalFieldName")%><br/>
			==========<br/>
			--%> <%
 	for (int i = 0; i < list.size(); i++) {
 %> <%=list.get(i)%> <br /> <%
 	}
 %>
			</td>
			<%
				//}
							//}
						}
			%>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>
</body>
</html>