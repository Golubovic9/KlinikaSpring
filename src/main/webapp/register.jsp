<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:if test="${!empty postoji }">${postoji }</c:if>
<sf:form modelAttribute="user"  action="register"  method="post">
  <table>
  	<tr>
  	   <td>Ime:</td><td><sf:input  path="ime"/>
  	   </td>
  	</tr>
  	<tr>
  	   <td>Prezime:</td><td><sf:input  path="prezime"/>
  	   </td>
  	</tr>
  	<tr>
  	   <td>Datum rodjenja(popunjava samo pacijent):</td><td><input type="date" name="datum"></td>
  	</tr>
 	<tr>
  	   <td>Adresa(popunjava samo pacijent):</td><td><input type="text" name="adresa"></td>
  	</tr>
  	<tr>
  	   <td>Specijalnost(popunjava samo lekar):</td><td><input type="text" name="spec"></td>
  	</tr>
  	<tr>
  	   <td>Korisnicko ime:</td><td><sf:input  path="korisnickoIme"/>
  	   </td>
  	</tr>
  	<tr>
  	   <td>Sifra:</td><td><sf:password  path="lozinka"/></td>
  	</tr>
  	<tr>
  	  <td>
  	   Uloga
  	  </td>
  	  <td>
  	  <select name="uloga"  >
  	  		<c:forEach items="${roles }" var="el">
  	  			<option value="${el.idUloga }" >${el.naziv }</option>
  	  		</c:forEach>
  	  </select>
	   </td>
	</tr>
  	<tr><td/><td><input type="submit" value="registruj se"></tr>
  	</table>
</sf:form>
</body>
</html>