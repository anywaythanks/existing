<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head><title>Products</title></head>
<body>
<h1>Products</h1>

<c:choose>
    <c:when test="${isLogin}">
        <p>You are logged in. Roles:
            <c:forEach items="${roles}" var="r"><c:out value="${r}"/> </c:forEach>
        </p>
        <p>
                <c:url var="profileUrl" value="/profile/${pageContext.request.userPrincipal.name}"/>
            <a href="${profileUrl}">My profile</a> |
        <p>
            <a href="<c:url value='/'/>">Home</a>
        <form action="<c:url value='/signout'/>" method="post" style="display:inline">
            <input type="submit" value="Logout"/>
        </form>
        </p>
    </c:when>
    <c:otherwise>
        <p><a href="<c:url value='/login'/>">Login</a> |
            <a href="<c:url value='/register'/>">Register</a></p>
    </c:otherwise>
</c:choose>

<table border="1" cellpadding="4">
    <tr>
        <th>Name</th><th>Description</th><th>Cost</th><th>In stock</th>
        <c:if test="${isLogin}"><th>Buy</th></c:if>
    </tr>
    <c:forEach items="${products}" var="p">
        <tr>
            <td><c:out value="${p.visibleName}"/></td>
            <td><c:out value="${p.description}"/></td>
            <td><fmt:formatNumber value="${p.cost}" type="number" minFractionDigits="2"/></td>
            <td><c:out value="${p.quantity}"/></td>
            <c:if test="${isLogin}">
                <td>
                    <c:url var="buyUrl" value="/products/buy/${p.name}"/>
                    <form action="${buyUrl}" method="post">
                        <input type="number" name="quantity" value="1" min="1" max="${p.quantity}"/>
                        <input type="submit" value="Buy"/>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>

<c:url var="prevUrl" value="/products">
    <c:param name="offset" value="${offset - limit < 0 ? 0 : offset - limit}"/>
    <c:param name="limit"  value="${limit}"/>
</c:url>
<c:url var="nextUrl" value="/products">
    <c:param name="offset" value="${offset + limit}"/>
    <c:param name="limit"  value="${limit}"/>
</c:url>
<p>
    <c:if test="${offset > 0}"><a href="${prevUrl}">&laquo; Prev</a></c:if>
    <c:if test="${not empty products and fn:length(products) == limit}">
        <a href="${nextUrl}">Next &raquo;</a>
    </c:if>
</p>
</body>
</html>