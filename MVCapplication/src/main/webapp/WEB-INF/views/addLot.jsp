<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="col-md-offset-2 col-md-8">


    <form:form role="form" method="post" commandName="lot" enctype="multipart/form-data">

        <spring:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="name" class="control-label">Enter lot name</form:label>
                <form:input path="name" class="form-control" placeholder="Name"/>
                <form:errors path="name" class="control-label"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="image">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="image.multipartFile" class="control-label">Image</form:label>
                <form:input path="image.multipartFile" type="file" accept="image/gif, image/jpeg, image/png" class="img"/>
                <div id="img-container">
                    <img id="blah" src="#" alt="your image" />
                    <span class="glyphicon glyphicon-remove" aria-hidden="true" title="remove image"></span>
                </div>
                <div class="img">
                    <span class="glyphicon glyphicon-upload" aria-hidden="true" title="add image"></span>
                </div>
                <form:errors path="image" class="control-label"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="description">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="description" class="control-label">Description</form:label>
                <form:textarea path="description" class="form-control" rows="3" maxLength="255"
                               style="margin: 0px -226px 0px 0px; height: 111px;"></form:textarea>
                <form:errors path="description" class="control-label"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="categories">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="categories" class="control-label">Choose categories</form:label>
                <form:select path="categories" class="form-control" multiple="false">
                    <form:options items="${categories}"  itemLabel="name" itemValue="id"></form:options>
                </form:select>
                <form:errors path="name" class="control-label"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="price">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="price" class="control-label">Initial price</form:label>
                <div class="input-group">
                    <span class="input-group-addon">$</span>
                    <form:input path="price" type="number" class="form-control" min="0" step="0.01"/>
                </div>
                <form:errors path="price" class="control-label"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="diff">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="diff" class="control-label">Difference per day</form:label>
                <div class="input-group">
                    <span class="input-group-addon">$</span>
                    <form:input path="diff" type="number" class="form-control" min="0" step="0.01"/>
                </div>
                <form:errors path="diff" class="control-label"></form:errors>
            </div>
        </spring:bind>

        <form:button type="submit" class="btn btn-default">Submit Button</form:button>
        <form:button type="reset" class="btn btn-default">Reset Button</form:button>

    </form:form>


</div>


