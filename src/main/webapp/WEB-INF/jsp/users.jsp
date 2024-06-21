<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navBar.jsp" %>

<html>
<head>
    <link rel="stylesheet" href="/css/stylesheet.css">
    <title>Title</title>
</head>
<body>

<div class="table_list">
    <c:forEach var="user" items="${users}" varStatus="counter">
        <a href="/users/${user.id}" methods="get" class="table_list_row">
            <div class="table_list_cell">${counter.count}</div>
            <div class="table_list_cell">${user.firstName} ${user.lastName}</div>
            <div class="table_list_cell">${user.email}</div>
            <div class="table_list_cell">${user.role.toString()}</div>
        </a>
        <form method="post" action="/users/delete/${user.id}">
            <input type="submit" value="delete">
        </form>
        <form method="get" action="/orders/user_id/${user.id}">
            <input type="submit" value="orders">
        </form>
        <form method="get" action="/users/edit/${user.id}">
            <input type="submit" value="edit">
        </form>
        <br/>
    </c:forEach>
</div>
</body>
</html>
