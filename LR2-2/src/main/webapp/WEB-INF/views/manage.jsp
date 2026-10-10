<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sf"  uri="http://www.springframework.org/tags/form" %>
<html>
<head><title>Manage products</title></head>
<body>
<h1>Manage products</h1>
<p>Seller: <c:out value="${pageContext.request.userPrincipal.name}"/></p>

<h2>Your products</h2>
<c:choose>
  <c:when test="${empty products}">
    <p><em>You have no products yet. Create one below.</em></p>
  </c:when>
  <c:otherwise>
    <table border="1" cellpadding="4">
      <tr>
        <th>Name</th>
        <th>Visible name</th>
        <th>Description</th>
        <th>Cost</th>
        <th>In stock</th>
        <th>Delivery</th>
      </tr>
      <c:forEach items="${products}" var="p">
        <tr>
          <td><c:out value="${p.name}"/></td>
          <td><c:out value="${p.visibleName}"/></td>
          <td><c:out value="${p.description}"/></td>
          <td><fmt:formatNumber value="${p.cost}" type="number" minFractionDigits="2"/></td>
          <td><c:out value="${p.quantity}"/></td>
          <td>
            <c:url var="deliveryUrl" value="/manage/delivery/${p.name}"/>
            <form action="${deliveryUrl}" method="post">
              <input type="number" name="quantity" value="1" min="1"/>
              <input type="submit" value="Deliver"/>
            </form>
          </td>
        </tr>
      </c:forEach>
    </table>
  </c:otherwise>
</c:choose>

<h2>Create new product</h2>
<c:url var="createUrl" value="/manage/create"/>
<sf:form action="${createUrl}" method="post" modelAttribute="product">
  <table cellpadding="4">
    <tr>
      <td><label for="name">Name (URL-safe):</label></td>
      <td><sf:input path="name" id="name" required="required"/></td>
    </tr>
    <tr>
      <td><label for="visibleName">Visible name:</label></td>
      <td><sf:input path="visibleName" id="visibleName" required="required"/></td>
    </tr>
    <tr>
      <td><label for="description">Description:</label></td>
      <td><sf:input path="description" id="description"/></td>
    </tr>
    <tr>
      <td><label for="cost">Cost:</label></td>
      <td><sf:input path="cost" id="cost" type="number" step="0.01" min="0.01" required="required"/></td>
    </tr>
    <tr>
      <td><label for="quantity">Initial quantity:</label></td>
      <td><sf:input path="quantity" id="quantity" type="number" min="0" value="0"/></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="Create"/></td>
    </tr>
  </table>
</sf:form>

<p><a href="<c:url value='/products'/>">Back to products</a></p>
</body>
</html>