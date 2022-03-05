<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="/Library/auth/pocetna" style="float: right; margin: 10px;"><img style="width: 50px;" src="${pageContext.request.contextPath}/img/home.png"></a>
	<form action="/Library/lekar/chooseDoctor" method="post">
		<select name="doc">
			<c:forEach items="${doctors }" var="el">
				<option value="${ el.idLekar}">${el.ime } ${el.prezime } </option>
			</c:forEach>
		</select>
		<input type="submit" value="odaberi">
	</form>
	<br>
	 <c:if test="${!empty izabrani }">
	 	Odabrali ste lekara:
		<table>
			<c:forEach items="${izabrani }" var="el">
				<tr>
					<td>${el.ime }</td>
					<td>${el.prezime }</td>
				</tr>
			</c:forEach>
		</table>
	</c:if> 
</body>
</html>