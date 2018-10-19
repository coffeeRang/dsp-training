<%@page import="org.json.simple.JSONObject"%>

<%@page import="java.util.*"%>

<%@page import="sy.training1.day1018.TestMain"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>KPI</title>

</head>

<body>

	<!-- TestMain을 선언해주고 쓸수있게 만들어준다.  keyset()만큼 돌리는 루프로 감싸준다. -->

	<%
		HashMap<String, Object> jsonO = (HashMap<String, Object>) TestMain.getJson();

		for (String key : jsonO.keySet()) {
			HashMap<String, Object> map1 = (HashMap<String, Object>) jsonO.get(key);
	%>

	<!-- 회사 이름을 불러온다. -->
	<h2><%=map1.get("companyName")%></h2>
	<hr style="border: solid 1px black;">
	<table border="1" width="100%">
		<tr>
			<td rowspan="2" style="text-align: center;">직책</td>
			<td colspan="2" style="text-align: center;">KPI</td>
		</tr>
		<tr>
			<td style="text-align: center;">구분</td>
			<td style="text-align: center;">성과지표</td>
		</tr>

		<%
			// 직책 이름
				for (String key2 : map1.keySet()) {
					if (map1.get(key2) != map1.get("companyName")) {
						HashMap<String, Object> map2 = (HashMap<String, Object>) map1.get(key2);
		%>
		<tr>
			<td style="text-align: center;" rowspan="<%=map1.size()-1%>"><%=map2.get("positionName")%></td>
			<%-- <td style="text-align: center;"><%=map2.get("positionName")%></td> --%>
		<!-- </tr>
		<tr> -->
			<%
				//구분이름
							for (String key3 : map2.keySet()) {

								if (map2.get(key3) != map2.get("positionName")) {

									HashMap<String, Object> map3 = (HashMap<String, Object>) map2.get(key3);

									List<String> list1 = (ArrayList<String>) map3.get("list");
			%>

			<td style="text-align: center;" colspan="1"><%=map3.get("evalFieldName")%></td>

			<td colspan="1">
				<% for (int i = 0; i < list1.size(); i++) {%>
				 <%=list1.get(i)%><br />
				  <% }%>
			</td>
		</tr>
		<%
						}
					}
				}
			}
		%>

	</table>
	<%
		}
	%>
</body>
</html>