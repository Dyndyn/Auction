<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="col-md-9">

    <div class="thumbnail">
        <c:if test="${not empty lot.imageId}">
            <img class="img-responsive" src="<c:url value="/image?imageId=${lot.imageId}"/>" alt="">
        </c:if>
        <c:if test="${empty lot.imageId}">
            <img class="img-responsive" src="/img/question.jpg" alt="">
        </c:if>
        <div class="caption-full">
            <h4 class="pull-right">$${lot.price}</h4>
            <h4><a href="#">${lot.name}</a>
            </h4>
            <p>${lot.description}</p>
        </div>
        <div class="ratings">

            <form:form id="rating" role="form" action="/addRating" method="post" commandName="rating">
                <form:hidden path="id.lotId" id="lotId" value="${lot.id}"/>
                <form:hidden path="rating" id="ratingVal"/>
            </form:form>
            <p class="pull-right">${lot.reviews} reviews</p>
            <div class="lead evaluation">
                <div id="colorstar" class="starrr ratable" value="${lot.rating}"></div>
                <span id="count">${lot.rating}</span> star(s) - <span id="meaning"> </span>

            </div>
        </div>
    </div>

    <div class="well">
        <c:set var="user" value="${userService.getCurrentUserFromSession()}"/>
        <c:if test="${user != null}">
            <div class="row comment">

                <form:form id="comment" role="form" action="/comment" method="post" commandName="comment">
                    <spring:bind path="data">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:textarea path="data" class="form-control" rows="3" maxLength="255"
                                           style="margin: 0px -226px 0px 0px; height: 111px;"></form:textarea>
                            <form:errors path="data" class="control-label"></form:errors>
                        </div>
                    </spring:bind>
                    <form:hidden path="lot.id" value="${lot.id}"></form:hidden>
                    <form:hidden path="user.id" value="${user.id}" id="userId"></form:hidden>

                    <div class="pull-left">
                        <span id="typing"></span>
                        <span id="dots"></span>
                    </div>
                    <div class="text-right">
                        <form:button class="btn btn-success">Leave a Review</form:button>
                    </div>
                </form:form>
            </div>

            <hr>
        </c:if>
        <c:forEach var="temp" items="${comments}" varStatus="loop">
            <div class="row">
                <div class="col-md-12">
                        ${temp.user.name}
                    <span class="pull-right">${temp.date.getTime()} days ago</span>
                    <p>${temp.data}</p>
                </div>
            </div>

            <c:if test="${loop.end ne loop.count}">
            <hr>
            </c:if>
        </c:forEach>

    </div>

</div>


