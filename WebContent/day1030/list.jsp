<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="gun.training2.day1030.JsonSimple"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	//json을 받아올 객체
	JsonSimple jsonSimple = new JsonSimple();

	//뿌려줄 값들이 들어있는 map
	//companyNum을 key로 가짐
	Map<String, Object> jsonMap = (HashMap<String, Object>) jsonSimple.getJson();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>json 테스트 페이지</title>
<style>
.content {
	text-align: left;
	padding-left: 30px;
	padding-top: 25px;
	padding-bottom: 25px;
}

.table {
	width: 100%;
	text-align: center;
	border-collapse: collapse;
}
</style>
</head>
<body>
	<%
		//jsonMap 안에 키의 갯수만큼 반복 
		//예) 100, 200, 300 = 3번
		for (String companyNum : jsonMap.keySet()) {

			//companyName, positionCd를 key로 갖는 map
			Map<String, Object> comNumMap = null;

			//positionName, evalFieldCd를 key로 갖는 map
			Map<String, Object> posCdMap = null;

			//evalName, list를 key로 갖는 map
			Map<String, Object> evalCdMap = null;

			//뿌려줄 회사 이름이 들어갈 변수
			String companyName = "";

			//뿌려줄 직책 이름이 들어갈 변수
			String positionName = "";

			//뿌려줄 구분 이름이 들어갈 변수
			String evalFieldName = "";

			//뿌려줄 성과지표 리스트가 들어갈 변수
			List<String> list = null;

			comNumMap = (HashMap<String, Object>) jsonMap.get(companyNum);

			companyName = (String) comNumMap.get("companyName");
	%>
	<h2><%=companyName%></h2>
	<hr style="border: solid 1px black;">
	<table border="1" class="table">
		<tr>
			<th style="width: 20%;" rowspan="2">직책</th>
			<th style="width: 80%;" colspan="2">KPI</th>
		</tr>
		<tr>
			<th style="width: 20%;">구분</th>
			<th style="width: 80%;">성과지표</th>
		</tr>
		<%
			//comNumMap 안에 키의 갯수만큼 반복
				//예) HP01, HP02, HP03 = 3번
				for (String positionCd : comNumMap.keySet()) {
		%>
		<tr>
			<%
				//positionCd가 companyName이 아닐 경우
						if (!positionCd.equals("companyName")) {

							posCdMap = (HashMap<String, Object>) comNumMap.get(positionCd);

							positionName = (String) posCdMap.get("positionName");

							//직책명은 최초에 한번만 출력되어야 하므로 검사를 위한 조건 변수
							boolean isFirst = true;

							//posCdMap 안에 키의 갯수만큼 반복
							//예) HE01, HE02 = 2번
							for (String evalFieldCd : posCdMap.keySet()) {
			%>
			<%
				//isFirst가 true일 경우, 루프의 첫 번째를 의미.
								if (isFirst) {
			%>
			<td rowspan="<%=posCdMap.size() - 1%>"><%=positionName%></td>
			<%
				isFirst = false;
								}
			%>
			<%
				//evalFieldCd가 positionName이 아닐 경우
								if (!evalFieldCd.equals("positionName")) {

									evalCdMap = (HashMap<String, Object>) posCdMap.get(evalFieldCd);

									evalFieldName = (String) evalCdMap.get("evalFieldName");

									list = (ArrayList<String>) evalCdMap.get("list");
			%>
			<td><%=evalFieldName%></td>

			<td class="content">
				<%
					//성과지표가 들어있는 list의 갯수만큼 반복
										for (int i = 0; i < list.size(); i++) {
				%> <%=list.get(i)%> <br /> <%
 	}
 %>
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