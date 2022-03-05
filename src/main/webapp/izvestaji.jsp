<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="/Library/auth/pocetna" style="float: right; margin: 10px;"><img style="width: 50px;" src="${pageContext.request.contextPath}/img/home.png"></a>
<div class="sidenav">
<a href="/Library/pacijent/getAnkete.pdf">utisci pacijenata</a><br>
<a href="/Library/pacijent/oceneSvihLekara.pdf">rang lista lekara</a><br>
<a href="/Library/pacijent/preglediUMesecu.pdf">pregledi po mesecima</a>
</div>
</body>
</html>