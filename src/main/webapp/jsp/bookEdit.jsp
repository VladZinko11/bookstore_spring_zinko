<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navBar.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="/controller?command=book_edit">
  <p>
    <input type="text" name="author" value="${book.author}" placeholder="author" required>
  </p>
  <p>
    <input type="text" name="title" value="${book.title}" placeholder="title" required>
  </p>
  <p>
    <input type="text" name="isbn" value="${book.isbn}" placeholder="isbn" required>
  </p>
  <p>
    <input type="date" value="${book.publicationDate.toString()}" name="publication_date" required>
  </p>
  <p>
    <input type="number" value="${book.price}" name="price" placeholder="price" required>
  </p>
  <input type="hidden" name="id" value="${book.id}">
  <input type="submit" value="edit">
</form>
</body>
</html>
