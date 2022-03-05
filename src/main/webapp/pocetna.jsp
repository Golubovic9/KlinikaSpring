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
<div class="sidenav">
	<img style="width: 80px;" src="${pageContext.request.contextPath}/img/kovin.jpeg">
	<security:authorize access="hasRole('PACIJENT')">
	<a href="/Library/lekar/getDoctors">odaberi lekara</a>
	<a href="/Library/lekar/showDoctors">oceni lekara</a>
	<a href="/Library/feedback/utisak">ostavi svoj utisak o klinici</a>
	<a href="/Library/lekar/getApointment">zakazi pregled</a>
	</security:authorize>
	<security:authorize access="hasRole('LEKAR')">
	<a href="/Library/pacijent/showPatients">prikaz pacijenata</a><br>
	<a href="/Library/pacijent/getRate">prikazi ocenu</a><br>
	<a href="/Library/pacijent/pregledaj">danasnji pregledi</a><br>
	<a href="/Library/pacijent/izvestaji">izvestaji</a><br>
	</security:authorize>
	<a href="/Library/auth/logout">odjavi se</a>
</div>
<security:authorize access="hasRole('LEKAR')">
	<div class="center">
	
	<form action="/Library/pacijent/search" method="post">
	<table>
	<tr>
		<td>pretraga pacijenata</td>
	</tr>
	<tr>
		<td>unesite ime:</td>
		<td><input type="text" name="ime"></td>
	</tr>
	<tr>
		<td>unesite prezime:</td>
		<td><input type="text" name="prezime"></td>
	</tr>
	</table>
	<input type="submit" value="pretrazi">
	</form>
	<c:if test="${!empty patients }">
			<c:forEach items="${patients }" var="el">
				<tr>
					<td>${el.ime}</td>
					<td>${el.prezime}</td>
					<td>${el.datumRodjenja}</td>
					<td><a href="/Library/pacijent/dijagnoza?idp=${el.idPacijent}"><button>postavi dijagnozu</button></a></td>
				</tr>
			</c:forEach>
	</c:if>
	</div>
</security:authorize>
</body>
</html>