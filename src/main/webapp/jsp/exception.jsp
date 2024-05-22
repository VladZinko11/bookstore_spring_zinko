<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 04.05.2024
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navBar.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="css/stylesheet.css">
    <title>Title</title>
</head>
<body>
<h1 class="error">
    <c:if test="${pageContext.response.status!='200'}">
        <div class="error_status">
                ${pageContext.response.status}
        </div>
    </c:if>
    ${message.toString()}<br/>
</h1>
</body>
</html>
