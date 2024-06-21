<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/css/stylesheet.css">
    <title>Title</title>
</head>
<body>
<div class="nav-bar">
    <a href="/" class="nav-bar-a"> main page </a>
    <a href="/login" methods="get" class="nav-bar-b">login in</a>
    <a href="/logout" methods="get" class="nav-bar-b">login out</a>
    <a href="/users/all" methods="get" class="nav-bar-a">users</a>
    <a href="/books/all" methods="get" class="nav-bar-a">books</a>
    <a href="/orders/my_orders" methods="get" class="nav-bar-a">my orders</a>
    <a href="/books/create" methods="get" class="nav-bar-a">create book</a>
    <a href="/create" methods="get" class="nav-bar-b">registration</a>
    <a href="/orders/all" methods="get" class="nav-bar-a">orders</a>
    <a href="/orders/basket" methods="get" class="nav-bar-b">basket</a>

</div>
</body>
</html>
