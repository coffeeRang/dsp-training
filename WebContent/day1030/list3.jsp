<%@page import="gun.training2.day1030.JsonSimpleSale"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	JsonSimpleSale jsonSimple = new JsonSimpleSale();

	Map<String, Object> saleMap = jsonSimple.getJson();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>json 테스트 페이지</title>
<style>
table {
	border-collapse: collapse;
}

table td {
	padding: 15px;
}

.title {
	background-color: lightgrey;
}

.title th {
	height: 35px;
}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
</head>
<body>
	<center><h1>재무 테이블</h1></center>
	<table border="1" style="width: 90%; margin: 0 auto;">
		<tr class="title">
			<th>펼치기</th>
			<th>당기</th>
			<th>전기</th>
			<th>전년동기</th>
		</tr>
		<%
			for (String accountSbjtCd : saleMap.keySet()) {
				//account_name, std_cd를 key로 가짐
				//예)account_name, std_cd
				Map<String, Object> stdMap = null;

				//std_cd를 key로 가짐
				//예)S-100_01, S-100-02, S-100-03
				Map<String, Object> preMap = null;

				//계정명이 할당될 변수
				String account_sbjt_name = null;

				//당기 값이 할당될 변수
				Object now = null;
				//전기 값이 할당될 변수
				Object pre = null;
				//전년동기 값이 할당될 변수
				Object preYear = null;

				//std_name이 할당될 변수
				Object stdName = null;

				stdMap = (Map<String, Object>) saleMap.get(accountSbjtCd);

				account_sbjt_name = (String) stdMap.get("account_sbjt_name");

				now = stdMap.get("02");
				pre = stdMap.get("01");
				preYear = stdMap.get("16");
				
		%>
		<tr style="background-color: #f9f9f9;">
			<th style="text-align: left;"><button style="width: 25px; height: 20px;" id="<%=account_sbjt_name%>"><span class="ui-button-text">+</span></button><%=account_sbjt_name%></th>
			<td style="text-align: right;"><%=now%></td>
			<td style="text-align: right;"><%=pre%></td>
			<td style="text-align: right;"><%=preYear%></td>
		</tr>
		<%
			//stdMap 안에 key의 갯수만큼 반복
				for (String stdCd : stdMap.keySet()) {
					//key 값이 std_cd 일 경우에만 실행하기 위함
					if (!stdCd.equals("account_sbjt_name") && !stdCd.equals("02") && !stdCd.equals("01")
							&& !stdCd.equals("16")) {
						preMap = (Map<String, Object>) stdMap.get(stdCd);

						now = preMap.get("02");
						pre = preMap.get("01");
						preYear = preMap.get("16");
						stdName = preMap.get("std_name");
		%>
		<tr>
			<td class="<%=account_sbjt_name%>hidden" style="text-align: left; display: none;"><%=stdName%></td>
			<td class="<%=account_sbjt_name%>hidden" style="text-align: right; display: none;"><%=now%></td>
			<td class="<%=account_sbjt_name%>hidden" style="text-align: right; display: none;"><%=pre%></td>
			<td class="<%=account_sbjt_name%>hidden" style="text-align: right; display: none;"><%=preYear%></td>
		</tr>
		<%
			}
				}
		%>
		<%
			}
		%>
	</table>
</body>
<script type="text/javascript">
	$(document).ready(function () { 
		// 페이지 document 로딩 완료 후 스크립트 실행 
		$("#매출액").click(function () { 
			status = $(".매출액hidden").css("display"); 
			
			if (status == "none") { 
				$(".매출액hidden").css("display", "");
				$("#매출액 span").text("-");
			} else { 
				$(".매출액hidden").css("display", "none"); 
				$("#매출액 span").text("+");
			} 
		});
		$("#매출원가").click(function () { 
			status = $(".매출원가hidden").css("display"); 
			
			if (status == "none") { 
				$(".매출원가hidden").css("display", "");
				$("#매출원가 span").text("-");
			} else { 
				$(".매출원가hidden").css("display", "none"); 
				$("#매출원가 span").text("+");
			} 
		}); 
		$("#매출총이익").click(function () { 
			status = $(".매출총이익hidden").css("display"); 
			
			if (status == "none") { 
				$(".매출총이익hidden").css("display", "");
				$("#매출총이익 span").text("-");
			} else { 
				$(".매출총이익hidden").css("display", "none"); 
				$("#매출총이익 span").text("+");
			} 
		}); 
	}); 
</script>

</html>