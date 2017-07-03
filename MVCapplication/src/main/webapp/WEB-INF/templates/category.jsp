<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-3">
    <p class="lead">Shop Name</p>
    <div class="list-group">
        <c:forEach var="category" items="${categories}" varStatus="loop">
        <a href="<c:url value='/category?id=${category.id}'/>" class="list-group-item">${category.name}</a>
        </c:forEach>
    </div>
</div>