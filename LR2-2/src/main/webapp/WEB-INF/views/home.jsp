<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Messenger</title>
    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/style.css" />" >
</head>
<body>
<h1>Welcome to Spittr</h1>
<a href="<c:url value="/messages" />">Messages</a> |
<a href="<c:url value="/register" />">Register</a>
</body>
</html>