<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>Request Password Reset</title>
        <meta name="layout" content="main" />
        <link rel="stylesheet" href="${resource(dir:'css/redmond',file:'jquery-ui-1.7.2.custom.css')}" media="screen, projection" />
    </head>
<body>
    <div class="jquery-ui content passwordReset">
        <g:form id="resetPassword" name="resetPassword" action="onResetPassword" class="ui-widget ui-corner-all">
            <h1 class="ui-widget-header">Request Password Reset</h1>
            <div class="ui-widget ui-state-highlight ui-corner-all">
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                <strong>No worries!</strong> Just enter the e-mail address you used when creating your account. We'll send you an e-mail with a link to reset your password</p>
            </div>
            <g:if test="${flash.message}">
                <div class="ui-widget ui-state-error ui-corner-all">
                    <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                    <strong>Alert:</strong> ${flash.message}</p>
                </div>
            </g:if>
            <fieldset>
                <label for="username">Please Enter Your Email Address</label>
                <input type="text" id="username" name="username" value="${username}" />
            </fieldset>
            <a class="submit" onClick="document.resetPassword.submit();" id="resetPassword">Reset Password &raquo;</a>
          </g:form>
      </div>
</body>
</html>

