<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>Request Password Reset</title>
        <meta name="layout" content="content-admin" />
    </head>
<body>
    <g:form id="resetpasswordform" name="resetpasswordform" action="onResetPassword" class="ui-widget ui-corner-all" style="height: 24em">
        <h1 class="ui-widget-header">Request Password Reset</h1>
        <div>
            <p><strong>No worries!</strong> Just enter the e-mail address you used when creating your account. We'll send you an e-mail with a link to reset your password</p>
        </div>
        <fieldset>
            <label for="username">Please Enter Your Email Address</label>
            <g:textField name="username" value="${username}" style="width:20em;" class="ui-corner-all"/>
        </fieldset>
        <g:if test="${flash.message}">
            <div class="ui-widget ui-state-error ui-corner-all">
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                <strong>Alert:</strong> ${flash.message}</p>
            </div>
        </g:if>
        <g:submitButton name="resetPassword" value="Reset Password »" id="resetPassword" class="ui-corner-all"/>
      </g:form>
</body>
</html>

