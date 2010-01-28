<html>
    <head>
        <title><g:message code="passwd.reset.request" default="Request Password Reset"/></title>
        <meta name="layout" content="content-admin" />
    </head>
<body>
    <g:form id="resetpasswordform" name="resetpasswordform" action="onResetPassword" class="ui-widget ui-corner-all" style="min-height: 17em">
        <h1 class="ui-widget-header"><g:message code="passwd.reset.request" default="Request Password Reset"/></h1>
        <div>
            <p><g:message code="passwd.reset.request.msg"/></p>
        </div>

        <fieldset>
            <label for="username"><g:message code="passwd.reset.request.lb"/></label>
            <g:textField name="username" value="${username}" style="width:20em;" class="ui-corner-all"/>
        </fieldset>
        <g:if test="${flash.message}">
            <p class="ui-widget ui-state-error ui-corner-all">
                <g:if test="${!flash.isError}">
                <strong><span class="ui-icon ui-icon-info" style="display:inline-block;"></span><g:message code="info"/></strong>
                </g:if>
                <g:else>
                <strong><span class="ui-icon ui-icon-alert" style="display:inline-block;"></span><g:message code="alert"/></strong>
                </g:else>
                <g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/></p>
        </g:if>
        <g:set var="submitBtnLabel"><g:message code="passwd.reset.submit"/></g:set>
        <g:submitButton name="resetPassword" value="${submitBtnLabel}" id="resetPassword" class="ui-corner-all"/>
      </g:form>
</body>
</html>

