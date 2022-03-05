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
	Ovde mozete ostaviti utisak o klinici,zamerku, predlog itd.
	<form action ="/Library/feedback/leaveFeedback" method="post">
		<textarea name="feedback" rows="4" cols="50"></textarea>
		<br>
		<input type="submit" value="posalji">
	</form>
	<br>
	<c:if test="${succes }">
		Hvala vam na ostavljenom utisku! Uzecemo u obzir.
	</c:if>
	<br>
	
</body>
</html>