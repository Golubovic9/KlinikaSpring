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
    <c:when test="${!empty pregledi}">
    	<table border="1">
        <c:forEach items="${pregledi }" var="el">
        	<tr>
        		<td>${el.pacijent.ime }</td>
        		<td> ${el.pacijent.prezime}</td>
        		<td> ${el.pacijent.datumRodjenja}</td>
        		<td><a href="/Library/pacijent/napisiRecept?pregled=${el.idPregled}"><button>izdaj recept</button></a></td>
        	</tr>
        </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        Danas nemate zakazan nijedan pregled.
    </c:otherwise>
</c:choose>
	
</body>
</html>