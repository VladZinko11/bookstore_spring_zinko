<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navBar.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="css/stylesheet.css">
    <title>Title</title>
</head>
<body>

<div class="table_list">
    <c:forEach var="book" items="${books}" varStatus="counter">
        <a href="/controller?command=book&&id=${book.id}" methods="get" class="table_list_row">
            <div class="table_list_cell">${counter.count}</div>
            <div class="table_list_cell">${book.author}</div>
            <div class="table_list_cell">${book.title}</div>
            <div class="table_list_cell">${book.isbn}</div>
            <div class="table_list_cell">${book.publicationDate.getYear()}</div>
        </a>
        <form>
            <input type="hidden" name="command" value="book_edit_form">
            <input type="hidden" name="id" value="${book.id}">
            <input type="submit" value="edit">
        </form>
        <form method="delete">
            <input type="hidden" name="command" value="book_delete">
            <input type="hidden" name="id" value="${book.id}">
            <input type="submit" value="delete">
        </form>
        <br/>
    </c:forEach>
</div>

</body>
</html>
