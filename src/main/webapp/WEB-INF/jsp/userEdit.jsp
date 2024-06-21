<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navBar.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="/users/edit">
  <p>
    <input type="text" name="firstName" value="${user.firstName}" placeholder="first name" required>
  </p>
  <p>
    <input type="text" name="lastName" value="${user.lastName}" placeholder="last name" required>
  </p>
  <p>
    <input type="email" value="${user.email}" name="email" placeholder="email@com" required>
  </p>
  <input type="password" name="password" minlength="4" value="${user.password}" placeholder="password" required>
  <p>
  <input  type="hidden" name="id" value="${user.id}">
  <input type="submit" value="edit">
</form>
</body>
</html>
