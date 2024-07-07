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
            <div class="table_list_cell">books</div>
            <div class="table_list_cell">status</div>
            <div class="table_list_cell">cost</div>
        </div>
        <div class="table_list_row"><br/></div>
        <c:forEach var="order" items="${orders}" varStatus="counter">
            <a href="/orders/${order.id}" methods="get" class="table_list_row">
                <div class="table_list_cell">${counter.count}</div>
                <div class="table_list_cell">${order.id}</div>
                <div class="table_list_cell">
                    <c:forEach var="orderItem" items="${order.orderItemsDto}">
                        <div>
                                ${orderItem.bookDto.author}<br/>
                                ${orderItem.bookDto.title}<br/>
                                ${orderItem.bookDto.isbn}<br/>
                            quantity: ${orderItem.quantity}<br/>
                        </div>
                        <div><br/></div>
                    </c:forEach>
                </div>
                <div class="table_list_cell">${order.status}</div>
                <div class="table_list_cell">${order.cost}$</div>
            </a>
            <div class="table_list_row"><br/></div>
        </c:forEach>
    </div>
</div>
</body>
</html>


