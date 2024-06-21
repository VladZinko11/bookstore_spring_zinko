<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navBar.jsp"%>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post" action="/login">
  <p>
    <input type="email" name="email" placeholder="email@com" required>
  </p>
  <input type="password" name="password" minlength="4" placeholder="password" required>
  <p>
    <input type="submit" value="login">
  </p>
</form>
</body>
</html>
