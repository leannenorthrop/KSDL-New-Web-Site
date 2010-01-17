<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>Change Password</title>
        <meta name="layout" content="main" />
        <link rel="stylesheet" href="${resource(dir:'css/redmond',file:'jquery-ui-1.7.2.custom.css')}" media="screen, projection" />
    </head>
<body>
    <div class="jquery-ui content passwordReset">
        <g:form id="changePassword" name="changePassword" action="changePassword" class="ui-widget ui-corner-all">
            <input type="hidden" name="id" value="${user?.passwordReset}" />
            <input type="hidden" name="reset" value="${user?.passwordReset}" />
            <h1 class="ui-widget-header">Change Password</h1>
            <g:if test="${flash.message}">
                <div class="ui-widget ui-state-error ui-corner-all">
                    <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                    <strong>Alert:</strong> ${flash.message}</p>
                </div>
            </g:if>
            <fieldset>
                <label for="password">New Password</label>
                <input type="password" id="password" name="password" value="" class="required"/>
            </fieldset>
            <fieldset>
                <label for="password">Password (again)</label>
                <input type="password" id="password" name="password2" value="" class="required"/>
            </fieldset>
            <a class="submit" onClick="document.changePassword.submit();" id="changePassword">Save New Password &raquo;</a>
          </g:form>
      </div>
</body>
</html>

