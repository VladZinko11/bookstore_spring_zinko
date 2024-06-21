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
    <div class="order_owner">${order.userDto.firstName} ${order.userDto.lastName}</div>
    <div class="table_list">
        <div class="table_list_row">
            <div class="table_list_cell">№</div>
            <div class="table_list_cell">Book</div>
            <div class="table_list_cell">quantity</div>
            <div class="table_list_cell">price</div>
        </div>

        <c:forEach var="orderItem" items="${order.orderItemsDto}" varStatus="counter">
            <div class="table_list_row">
                <div class="table_list_cell">${counter.count}</div>
                <div class="table_list_cell">${orderItem.bookDto.author}<br/>
                        ${orderItem.bookDto.title}<br/>
                        ${orderItem.bookDto.isbn}<br/>
                </div>
                <div class="table_list_cell">${orderItem.quantity}</div>
                <div class="table_list_cell">${orderItem.price}$</div>
            </div>
            <div class="table_list_row"><br/></div>
        </c:forEach>
    </div>
    <div class="order_cost ">order cost: ${order.cost}$</div>
    <div class="order_status">status: ${order.status.toString()}</div>
</div>
</body>
</html>

