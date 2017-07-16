<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div class="col-md-12">
    <h2>Your lots</h2>
    <div class="table-responsive">
        <table class="table table-bordered table-hover table-striped">
            <thead>
            <tr>
                <th>name</th>
                <th>price</th>
                <th>rating</th>
                <th>reviews</th>
                <th>image</th>
                <th>active</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="lot" items="${lots}" varStatus="loop">

                    <tr>
                        <td class="align-middle"><a href="<c:url value='/lot?id=${lot.id}'/>">${lot.name}</a></td>
                        <td class="align-middle">${lot.price}</td>
                        <td class="align-middle">${lot.rating}</td>
                        <td class="align-middle">${lot.reviews}</td>
                        <td class="text-center"><a href="<c:url value='/lot?id=${lot.id}'/>">
                            <img class="img" src="<c:url value="/image?imageId=${lot.imageId}"/>"
                                 alt="">
                        </a></td>
                        <td class="align-middle text-center">
                            <form:form action="/lot/enable" commandName="enable">
                                <form:hidden path="id" value="${lot.id}"/>
                                <input type="checkbox" name="enabled" id="enabled" class="form-control"
                                <c:out value='${lot.enabled == true ? "checked" : ""}'/>/>
                            </form:form>

                        </td>
                    </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</div>