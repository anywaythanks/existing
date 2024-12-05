<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Messenger</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" >
</head>
<body>
<div class="spittleView">
    <div class="spittleMessage"><c:out value="${message.message}"/></div>
    <div>
        <span class="spittleTime"><c:out value="${message.date}"/></span>
    </div>
</div>
</body>
</html>
