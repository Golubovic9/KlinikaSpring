<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="/Library/auth/pocetna" style="float: right; margin: 10px;"><img style="width: 50px;" src="${pageContext.request.contextPath}/img/home.png"></a>
	<c:choose>
	<c:when test="${!empty prosecna }">
		 Vasa ocena je ${prosecna }
	</c:when>
	<c:otherwise>
		Nijedan pacijent vas nije ocenio do sada.
	</c:otherwise>
	</c:choose>
</body>
</html>