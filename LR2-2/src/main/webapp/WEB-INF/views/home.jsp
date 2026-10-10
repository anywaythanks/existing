<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page import="com.model.Role" %>

<html>
<head>
    <title>Messenger</title>
    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/style.css" />" >
</head>
<body>
<h1>Welcome</h1>
<c:if test="${isLogin and roles.contains(Role.SALESMAN.getName())}">
    <a href="<c:url value="/manage" />">Manage</a> |
</c:if>
<a href="<c:url value="/products" />">Products</a> |
<a href="<c:url value="/register" />">Register</a> |
<c:if test="${not isLogin}">
<a href="<c:url value="/login" />">Login</a>
</c:if>
<c:if test="${isLogin}">
    <a href="<c:url value="/signout" />">Logout</a>
</c:if>
</body>
</html>