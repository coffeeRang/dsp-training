<%@page import="sy.training2.day1023.SetClass"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<%
		try{
		%>
		
	<table border="1">
		<%
			out.println(SetClass.getString());
		%>
	</table>
	
	<%
		}catch(Exception e){
	
	%>
		<h1>Json파일을 찾을수 없습니다.</h1>
	<%
		}
	%>

</body>
</html>