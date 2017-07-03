<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<c:url value='/home'/>">Home page</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="<c:url value='/about'/>">About</a>
                </li>
                <li>
                    <a href="<c:url value='/contact'/>">Contact</a>
                </li>
            </ul>
            <ul class="nav navbar-nav right">
                <c:set var="user" value="${userService.getCurrentUserFromSession()}"/>
                <c:if test="${user != null}">
                    <li>
                        <a href="<c:url value='/login'/>">Log out</a>
                    </li>
                    <li class="dropdown">
                        <a href="<c:url value='/user'/>">${user.name}</a>
                        <div class="dropdown-content">
                            <a href="<c:url value='/user'/>">Account</a>
                            <a href="<c:url value='/addLot'/>">Add Lot</a>
                            <a href="<c:url value='/myLots'/>">My lots</a>
                        </div>
                    </li>
                </c:if>
                <c:if test="${user == null}">
                    <li>
                        <a href="<c:url value='/login'/>">Log in</a>
                    </li>
                    <li>
                        <a href="<c:url value='/register'/>">Sign Up</a>
                    </li>
                </c:if>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>
