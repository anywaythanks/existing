<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page session="false" %>
<%@ page import="com.model.Role" %>
<html>
<head>
    <title>LR</title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resources/style.css" />">
</head>
<body>
<h1>Register</h1>
<form method="POST">
    <sf:errors path="account.*" element="div" cssClass="errors"/>
    <sf:label path="account.name"
              cssErrorClass="error">Login</sf:label>:
    <sf:input path="account.name" cssErrorClass="error"/><br/>
    <sf:label path="account.passwd"
              cssErrorClass="error">Password</sf:label>:
    <sf:password path="account.passwd" cssErrorClass="error"/><br/>
    <sf:radiobuttons path="account.role" items="${Role.ROLE_MAP}" delimiter="<br/>"/><br/>
    <input type="submit" value="Register"/>
</form>
</body>
</html>