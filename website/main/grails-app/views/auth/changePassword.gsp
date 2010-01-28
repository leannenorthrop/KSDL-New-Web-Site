<html>
    <head>
        <title><g:message code="passwd.change" default="Change Password"/></title>
        <meta name="layout" content="content-admin" />
    </head>
<body>
    <g:form id="changePassword" name="changePassword" action="changePassword" class="ui-widget ui-corner-all" style="min-height: 20em;">
        <g:hiddenField name="id" value="${user?.passwordReset}" />
        <g:hiddenField type="hidden" name="reset" value="${user?.passwordReset}" />
        <h1 class="ui-widget-header"><g:message code="passwd.change" default="Change Password"/></h1>
        <g:if test="${flash.message}">
            <p class="ui-widget ui-state-error ui-corner-all"><span class="ui-icon ui-icon-alert" style="display: inline-block;"></span>
            <strong><g:message code="alert"/></strong><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/></p>
        </g:if>
        <fieldset>
            <label for="password"><g:message code="passwd.un.new"/></label>
            <g:passwordField name="password" value="" class="required ui-corner-all"/>
        </fieldset>
        <fieldset>
            <label for="password2"><g:message code="passwd.un.new.again"/></label>
            <g:passwordField name="password2" value="" class="required ui-corner-all"/>
        </fieldset>
        <g:set var="submitBtnLabel"><g:message code="passwd.save.btn"/></g:set>
        <g:submitButton name="changePasswordBtn" value="${submitBtnLabel}" id="changePasswordBtn" class="ui-corner-all"/>
    </g:form>
</body>
</html>

