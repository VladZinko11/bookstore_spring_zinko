<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navBar.jsp" %>
<html>
<head>
    <title>UserCreate</title>
</head>
<body>
<form method="post" action="/create">
    <p>
        <input class="input_email" type="email" name="email" placeholder="email@com" required>
    </p>
    <input type="password" name="password" minlength="4" placeholder="password" required>
    <p>
        <input type="submit" value="edit">
    </p>
</form>
</body>
</html>
