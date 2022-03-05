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
	<form action="/Library/pacijent/izdajRecept" method="post">
		<label for="naziv">naziv leka:</label>
		<input type="text" name="naziv">
		<label for="kolicina">kolicina:</label>
		<input type="number" name="kolicina">
		<input type="submit" value="izdaj">
	</form>
</body>
</html>