<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navBar.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="/controller?command=user_create">
<p>
  <input type="email" name="email" placeholder="email@com" required>
</p>
<input type="password" name="password" minlength="4" placeholder="password" required>
<p>
  <input type="submit" value="edit">
</p>
</form>
</body>
</html>
