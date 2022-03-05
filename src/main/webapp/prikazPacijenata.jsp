<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
                <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="/Library/auth/pocetna" style="float: right; margin: 10px;"><img style="width: 50px;" src="${pageContext.request.contextPath}/img/home.png"></a>
	<table border="1">
		<c:forEach items="${pacijenti }" var="el">
			<tr>
				<td>${el.ime}</td>
				<td>${el.prezime}</td>
				<td>${el.datumRodjenja}</td>
				<td><a href="/Library/pacijent/dijagnoza?idp=${el.idPacijent}"><button>postavi dijagnozu</button></a></td>
				<td><a href="/Library/pacijent/getKarton.pdf?idp=${el.idPacijent}&ime=${el.ime}&prezime=${el.prezime}"><button>prikazi karton</button></a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>