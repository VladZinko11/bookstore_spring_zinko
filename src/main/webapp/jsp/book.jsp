<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 04.05.2024
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navBar.jsp"%>
<html>
<head>
    <link rel="stylesheet" href="css/stylesheet.css">
    <title>Title</title>
</head>
<body>
    <div class="book">
        <div class="book_name">${book.author}</div>
        <div class="book_name">${book.title}</div>
        <div class="book_isbn ">${book.isbn}</div>
        <div class="book_date">${book.publicationDate.getYear()}</div>
    </div>
</body>
</html>
