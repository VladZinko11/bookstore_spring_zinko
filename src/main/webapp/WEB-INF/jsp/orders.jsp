<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navBar.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="/css/stylesheet.css">
    <title>Title</title>
</head>
<body>
<div class="order">
    <div class="table_list">
        <div class="table_list_row">
            <div class="table_list_cell">№</div>
            <div class="table_list_cell">order number</div>
            <div class="table_list_cell">owner of order</div>
            <div class="table_list_cell">status</div>
            <div class="table_list_cell">cost</div>
        </div>
        <div class="table_list_row"><br/></div>
        <c:forEach var="order" items="${orders}" varStatus="counter">
            <a href="/orders/${order.id}" methods="get" class="table_list_row">
                <div class="table_list_cell">${counter.count}</div>
                <div class="table_list_cell">${order.id}</div>
                <div class="table_list_cell">${order.userDto.firstName} ${order.userDto.lastName}</div>
                <div class="table_list_cell">${order.status}</div>
            </a>
        </c:forEach>
    </div>
</div>
</body>
</html>
