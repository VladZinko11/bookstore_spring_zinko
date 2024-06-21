<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navBar.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="/css/stylesheet.css">
    <title>Error</title>
</head>
<body>
<h1 class="error">
    <div class="error_status">
        ${pageContext.response.status}
    </div>
    ${message.toString()}<br/>
</h1>
</body>
</html>
