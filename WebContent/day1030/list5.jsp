<%@page import="java.util.TreeMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="gun.training2.day1030.JsonSimpleAssets"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	JsonSimpleAssets jsonSimple = new JsonSimpleAssets();
	Map<String, Object> assetsMap = jsonSimple.getJson();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>json 테스트 페이지</title>
<style>
	.head{
		background-color: #a79393;
	}
	.head2{
		background-color: #bcbcbc;
	}
	.head3{
		background-color: #e6e6e6;
	}
	table td, th{
		padding: 20px;
	}
</style>
</head>
<body>
	<center><h1>재무 테이블</h1></center>
	<table border="1" style="width:90%; margin: 0 auto;">
		<tr class="head">
			<td style="text-align: center;">펼치기</td>
			<td style="text-align: center;">당기</td>
			<td style="text-align: center;">전기</td>
			<td style="text-align: center;">전년동기 </td>
		</tr>
<%
		for(String parentsCd : assetsMap.keySet()){
			//최상위 부모를 의미
			//부모이름, 자식코드, 구분코드를 key로 가짐
			Map<String, Object> parentsMap = null;

			//최상위 부모의 자식을 의미
			//자식이름, 구분코드, 손자코드를 key로 가짐
			Map<String, Object> childMap = null;

			//자식의 자식, 즉 손자를 의미
			//손자이름, 구분코드를 key로 가짐
			Map<String, Object> grandsonMap = null;
			
			parentsMap = (Map<String, Object>)assetsMap.get(parentsCd);
			
			%>
			<tr class="head2">
				<th style="text-align: left;"><%=parentsMap.get("account_sbjt_name")%></th>
				<td style="text-align: right;"><%=parentsMap.get("02")%></td>
				<td style="text-align: right;"><%=parentsMap.get("01")%></td>
				<td style="text-align: right;"><%=parentsMap.get("16")%></td>
			</tr>
			<%
			//parentsMap의 키의 수만큼 반복
			for(String childCd : parentsMap.keySet()){
				//std_cd일 경우.
				if(!childCd.equals("account_sbjt_name") && !childCd.equals("02") && !childCd.equals("01") && !childCd.equals("16")){
					childMap = (Map<String, Object>)parentsMap.get(childCd);
					
					%>
					<tr class="head3">
						<td><%=childMap.get("std_name")%></td>
						<td style="text-align: right;"><%=childMap.get("02")%></td>
						<td style="text-align: right;"><%=childMap.get("01")%></td>
						<td style="text-align: right;"><%=childMap.get("16")%></td>
					</tr>
					<%
					//childMap의 키의 수만큼 반복
					for(String grandsonCd : childMap.keySet()){
						//std_cd일 경우.
						if(!grandsonCd.equals("std_name") && !grandsonCd.equals("02") && !grandsonCd.equals("01") && !grandsonCd.equals("16")){
							
						grandsonMap = (Map<String, Object>)childMap.get(grandsonCd);
						
						%>
						<tr>
							<td><%=grandsonMap.get("std_name")%></td>
							<td style="text-align: right;"><%=grandsonMap.get("02")%></td>
							<td style="text-align: right;"><%=grandsonMap.get("01")%></td>
							<td style="text-align: right;"><%=grandsonMap.get("16")%></td>
						</tr>
						<%
					}
					}
				}
			}
		}
%>
	</table>
</body>
</html>