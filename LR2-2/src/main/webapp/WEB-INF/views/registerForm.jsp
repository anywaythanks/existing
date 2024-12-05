<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page session="false" %>
<html>
<head>
    <title>Spittr</title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resources/style.css" />">
</head>
<body>
<h1>Register</h1>
<form method="POST" enctype="multipart/form-data">
    <sf:errors path="user.*" element="div" cssClass="errors"/>
    <sf:label path="user.firstName"
              cssErrorClass="error">First Name</sf:label>:
    <sf:input path="user.firstName" cssErrorClass="error"/><br/>
    <sf:label path="user.lastName"
              cssErrorClass="error">Last Name</sf:label>:
    <sf:input path="user.lastName" cssErrorClass="error"/><br/>
    <sf:label path="user.email" type="email"
              cssErrorClass="error">Email</sf:label>:
    <sf:input path="user.email" cssErrorClass="error"/><br/>
    <sf:label path="user.login"
              cssErrorClass="error">Login</sf:label>:
    <sf:input path="user.login" cssErrorClass="error"/><br/>
    <sf:label path="user.password"
              cssErrorClass="error">Password</sf:label>:
    <sf:password path="user.password" cssErrorClass="error"/><br/>
    <label>Profile Picture</label>:
    <input type="file"
           name="profilePicture"
           accept="image/jpeg,image/png,image/gif"/><br/>
    <input type="submit" value="Register"/>
</form>
</body>
</html>