<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navBar.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="/controller?command=book_create">
  <p>
    <input type="text" name="author" placeholder="author" required>
  </p>
  <p>
    <input type="text" name="title" placeholder="title" required>
  </p>
  <p>
    <input type="text" name="isbn" placeholder="isbn" required>
  </p>
  <p>
    <input type="date" name="publication_date" required>
  </p>
  <p>
    <input type="number" name="price" placeholder="price" required>
  </p>
  <input type="submit" value="create">
</form>

</body>
</html>
