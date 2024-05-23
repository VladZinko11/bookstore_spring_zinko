<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navBar.jsp" %>

<html>
<head>
    <link rel="stylesheet" href="css/stylesheet.css">
    <title>Title</title>
</head>
<body>

<div class="table_list">
    <c:forEach var="user" items="${users}" varStatus="counter">
        <a href="/controller?command=user&&id=${user.id}" methods="get" class="table_list_row">
            <div class="table_list_cell">${counter.count}</div>
            <div class="table_list_cell">${user.firstName}</div>
            <div class="table_list_cell">${user.lastName}</div>
            <div class="table_list_cell">${user.email}</div>
            <div class="table_list_cell">${user.role.toString()}</div>
        </a>
        <form>
            <input type="hidden" name="command" value="user_edit_form">
            <input type="hidden" name="id" value="${user.id}">
            <input type="submit" value="edit">
        </form>
        <form method="delete">
            <input type="hidden" name="command" value="user_delete">
            <input type="hidden" name="id" value="${user.id}">
            <input type="submit" value="delete">
        </form><br/>
    </c:forEach>
</div>
</body>
</html>
