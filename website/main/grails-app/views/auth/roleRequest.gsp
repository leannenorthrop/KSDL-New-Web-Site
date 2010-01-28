<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>Request Access Right</title>
        <meta name="layout" content="content-admin" />
    </head>
<body>
    <g:form id="changePassword" name="changePassword" action="changePassword" class="ui-widget ui-corner-all" style="height: 20em;">
        <g:hiddenField name="id" value="${user?.passwordReset}" />
        <g:hiddenField type="hidden" name="reset" value="${user?.passwordReset}" />
        <h1 class="ui-widget-header">Change Password</h1>
        <g:if test="${flash.message}">
            <div class="ui-widget ui-state-error ui-corner-all">
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                <strong>Alert:</strong> ${flash.message}</p>
            </div>
        </g:if>
        <fieldset>
            <label for="password">New Password</label>
            <g:passwordField name="password" value="" class="required ui-corner-all"/>
        </fieldset>
        <fieldset>
            <label for="password2">Password (again)</label>
            <g:passwordField name="password2" value="" class="required ui-corner-all"/>
        </fieldset>
        <g:submitButton name="changePasswordBtn" value="Save New Password »" id="changePasswordBtn" class="ui-corner-all"/>
    </g:form>
</body>
</html>

