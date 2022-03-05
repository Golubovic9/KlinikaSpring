<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 	<img style="width: 130px;" src="${pageContext.request.contextPath}/img/kovin.jpeg">
	<div class="center">
	<c:url var="loginUrl" value="/login" />
	<c:if test="${not empty param.error}">
		
			<p>Pogresni podaci.</p>
		
	</c:if>
	<form action="${loginUrl}" method="post">
		<table>
			<tr>
				<td>Korisnicko ime</td>
				<td><input type="text" name="username"
					placeholder="Unesite korisnicko ime" required></td>
			</tr>
			<tr>
				<td>Sifra</td>
				<td><input type="password" name="password"
					placeholder="Unesite sifru" required></td>
			</tr>
			
			<tr>
				<td><input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" /></td>
				<td><input type="submit" value="Prijava"></td>
			</tr>
		</table><br/><br/>
		
	</form>
	<a href="/Library/auth/registerUser">registruj se</a>
	</div>
</body>
</html>