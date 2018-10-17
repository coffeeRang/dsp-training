<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="training1.UseJson"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>hello world</title>
</head>
<style>
  table {
    width: 100%;
    border: 1px solid #444444;
    border-collapse: collapse;
  }
  th, td {
    border: 1px solid #444444;
    padding: 10px;
  }
</style>
<%
	UseJson useJson = new UseJson();
	
	JSONObject jsonObj = useJson.getJsonFile();
	JSONArray jsonArr = (JSONArray) jsonObj.get("dataArray");
	
	Map<String, Object> resultMap = useJson.replaceFormat(jsonArr);
	
	Set<String> companyKeySet = resultMap.keySet();
%>
<body>

	<%
	for (String companyKey: companyKeySet) {
	%>
		<table>
	<%
		Map<String, Object> companyMap = (Map<String, Object>)resultMap.get(companyKey);
		Map<String, Object> positionMap = null;
		Map<String, Object> evalMap = null;
		ArrayList<String> indicatInfoList = null;
	%>
		<tr>
			<td colspan="3"><%=companyMap.get("companyName") %></td>
		</tr>
		<tr>
			<td rowspan="2" align="center" style="width: 20%">직책</td>
			<td colspan="2" align="center">KPI</td>
		</tr>
		<tr>
			<td align="center" style="width: 20%">구분</td>
			<td align="center">성과지표</td>
		</tr>
		
	<%
		System.out.println(">> 회사명 : " + companyMap.get("companyName"));
	
		Set<String> positionKeySet = companyMap.keySet();
		Set<String> evalKeySet = null;
		Set<String> indicatKeySet = null;
		for (String positionKey: positionKeySet) {
	%>
	<%
			if (positionKey.equals("companyName")) {
			} else {
				positionMap = (Map<String, Object>)companyMap.get(positionKey);
				System.out.println("\t직책명 : " + positionMap.get("positionName"));
				evalKeySet = positionMap.keySet();
	
				for (String evalKey: evalKeySet) {
					if (evalKey.equals("positionName")) {
					} else {
						evalMap = (Map<String, Object>)positionMap.get(evalKey);
						indicatKeySet = evalMap.keySet();
						
						for(String indicatKey: indicatKeySet) {
							if (!indicatKey.equals("list")) {
								System.out.println("\t\t지표구분 : " + evalMap.get("evalFieldName"));
	%>
								<tr>
									<td align="center"><%=positionMap.get("positionName") %></td>
									<td align="center"><%=evalMap.get("evalFieldName") %></td>
	<%
							} else {
	%>
								<td>
	<%
								indicatInfoList = (ArrayList<String>)evalMap.get(indicatKey);

								for (int i = 0; i < indicatInfoList.size(); i++) {
	%>
									<%=i + " : " + indicatInfoList.get(i)%><br>
	<%
									System.out.println("\t\t\t" + i + " : " + indicatInfoList.get(i));
								}// list for
	%>
								</td></tr>
	<%
								
							}

						}// indicatKeySet for
					}

				}// evelKeySet for

			}
			
	%>
		</tr>
	<%
		}// positionKeySet for
	%>
		</table>
		<br>
	<%		
		/* break; */
	}// companyKeySet for

	%>

	<br><br><br><br>

</body>
</html>