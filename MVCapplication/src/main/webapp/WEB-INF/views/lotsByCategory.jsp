<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-9">

    <div class="row carousel-holder">

        <div class="col-md-12">
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <c:set var="index" scope="page" value="0"/>
                    <c:forEach var="lot" items="${lots}" varStatus="loop">
                        <c:if test="${not empty lot.image.data}">
                            <li data-target="#carousel-example-generic"
                                data-slide-to="${index}" ${index == 0 ? "class=\"active\"" : ""}></li>
                            <c:set var="index" scope="page" value="${index+1}"/>
                        </c:if>
                    </c:forEach>
                </ol>
                <div class="carousel-inner">
                    <c:set var="index" scope="page" value="0"/>
                    <c:forEach var="lot" items="${lots}" varStatus="loop">
                        <c:if test="${not empty lot.image.data}">

                            <div class="item ${index == 0 ? "active" : ""}">
                                <img class="slide-image" src="${lot.image.data}" alt="">
                            </div>
                            <c:set var="index" scope="page" value="${index+1}"/>
                        </c:if>
                    </c:forEach>
                </div>
                <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left"></span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right"></span>
                </a>
            </div>
        </div>

    </div>

    <div class="row">

        <c:forEach var="lot" items="${lots}" varStatus="loop">
            <div class="col-sm-4 col-lg-4 col-md-4">
                <div class="thumbnail">
                    <img src="${lot.image.data != null ? lot.image.data : "/img/question.jpg"}" alt="">
                    <div class="caption">
                        <h4 class="pull-right">$${lot.price}</h4>
                        <h4><a href="<c:url value='/lot?id=${lot.id}'/>">${lot.name}</a>
                        </h4>
                        <p>${lot.description}</p>
                    </div>
                    <div class="ratings">
                        <p class="pull-right">${lot.reviews} reviews</p>
                        <p>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>

    </div>

</div>

