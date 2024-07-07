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
    <c:forEach var="book" items="${books}" varStatus="counter">
        <a href="/books/${book.id}" methods="get" class="table_list_row">
            <div class="table_list_cell">${counter.count}</div>
            <div class="table_list_cell">${book.author}</div>
            <div class="table_list_cell">${book.title}</div>
            <div class="table_list_cell">${book.isbn}</div>
            <div class="table_list_cell">${book.publicationDate.getYear()}</div>
            <div class="table_list_cell">${book.price}$</div>
        </a>
        <c:if test="${sessionScope.get(\"user\")!=null}">
            <form method="post" action="/orders/cart/add_book/${book.id}">
                <input type="submit" value="add book">
            </form>
            <c:if test="${sessionScope.get(\"user\").getRole().toString()==\"ADMIN\"}">
                <form method="get" action="/books/edit/${book.id}">
                    <input type="submit" value="edit">
                </form>
                <form method="post" action="/books/delete/${book.id}">
                    <input type="submit" value="delete">
                </form>
            </c:if>
        </c:if>
        <br/>
    </c:forEach>
</div>

</body>
</html>
