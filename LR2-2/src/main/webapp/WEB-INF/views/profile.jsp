<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head><title>Profile ${account.name}</title></head>
<body>
<a href="<c:url value='/'/>">Home</a>
<h1>Your Profile</h1>
Name: <c:out value="${account.name}"/><br/>
Amount: <fmt:formatNumber value="${account.amount}" type="number" minFractionDigits="2"/><br/>
Role: <c:out value="${account.role.name}"/>

<h2>Top up balance</h2>
<c:url var="topupUrl" value="/profile/${account.name}/topup"/>
<form action="${topupUrl}" method="post">
    <label>
        Amount:
        <input type="number" name="amount" step="0.01" min="0.01" value="10.00"/>
    </label>
    <input type="submit" value="Top up"/>
</form>
</body>
</html>