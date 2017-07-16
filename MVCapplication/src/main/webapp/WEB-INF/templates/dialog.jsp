<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="success" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header alert alert-success" role="alert">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><spring:message code="confirm"/></h4>
            </div>
            <div class="modal-body text-center">
                <spring:message code="sure"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-danger">No</button>
                <button type="button" class="btn btn-primary btn-confirm">Yes</button>
            </div>
        </div>
    </div>
</div>