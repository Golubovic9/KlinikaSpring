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
	<form action="/Library/lekar/zakaziPregled" method="post">
		<select name="doc">
			<c:forEach items="${lekari }" var="el">
				<option value="${el.idLekar }">${el.ime } ${el.prezime } ${el.specijalnost }</option>
			</c:forEach>
		</select>
		<input type="date" name="datum">
		<input type="submit" value="zakazi">
	</form>
	<c:if test="${!empty dejt }">
		${dejt }
	</c:if>
</body>
</html>