<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page isELIgnored="false" %>

<tiles:importAttribute name="stylesheets"/>
<tiles:importAttribute name="javascripts"/>

<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title><tiles:getAsString name="title"/></title>

    <c:forEach var="css" items="${stylesheets}">
        <link rel="stylesheet" type="text/css" href="<c:url value="${css}"/>">
    </c:forEach>
</head>
<body>
<tiles:insertAttribute name="header"/>
<!-- Page Content -->
<div class="container">

    <div class="row">
        <tiles:insertAttribute name="category"/>
        <tiles:insertAttribute name="body"/>
    </div>

</div>
<!-- /.container -->
<tiles:insertAttribute name="footer"/>
<tiles:insertAttribute name="dialog"/>
<tiles:insertAttribute name="error"/>

<c:forEach var="js" items="${javascripts}">
    <script src="<c:url value="${js}"/>"></script>
</c:forEach>

</body>
</html>
