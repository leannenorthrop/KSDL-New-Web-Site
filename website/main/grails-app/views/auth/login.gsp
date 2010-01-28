<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title>Sign In</title>
    </head>
<body>
    <g:if test="${flash.message}">
      <div class="ui-widget ui-state-error ui-corner-all">
          <p><span class="ui-icon ui-icon-alert" style="float: left;"></span>
          <strong>Alert:</strong> ${flash.message}</p>
      </div>
    </g:if>
    <table class="entryForms">
      <tr>
          <td class="ui-widget ui-corner-all signIn">
              <g:form id="signIn" name="signIn" action="signIn">
                  <h1 class="ui-widget-header">Sign In</h1>
                <fieldset>
                    <label for="username">Email</label>
                    <g:textField class="required ui-corner-all" name="username" value="${username}" />
                </fieldset>
                <fieldset>
                    <label for="password">Password</label>
                    <g:passwordField class="required ui-corner-all" name="password" value="${password}" /><br/>
                    <g:link action="resetPassword">Forgot your password?</g:link>
                </fieldset>
                <!--fieldset class="last">
                    <label class="rememberMe" for="rememberMe">Remember me?</label> <g:checkBox class="rememberMe" id="rememberMe" name="rememberMe" value="${rememberMe}" />
                </fieldset-->
                <g:hiddenField name="targetUri" value="${targetUri}" />
                <g:submitButton name="submitbtn" value="Sign In »" id="submitbtn" class="ui-corner-all"/>
              </g:form>
          </td>
          <td class="middle">
              or
          </td>
          <td class="ui-widget ui-corner-all register">
              <g:form id="register" name="register" action="register">
                <h1 class="ui-widget-header">Become a Member</h1>
                <fieldset>
                    <label for="username">Email</label>
                    <g:textField name="username" value="${username}" class="required ui-corner-all" />
                </fieldset>
                <fieldset>
                    <label for="password">Password</label>
                    <g:passwordField name="password" value="" class="required ui-corner-all" />
                </fieldset>
                <fieldset>
                    <label for="password">Password</label>
                    <g:passwordField id="password" name="password2" value="" class="required ui-corner-all" />
                </fieldset>
                <g:submitButton name="submitbtn" value="Request Account »" id="submitbtn" class="ui-corner-all"/>
              </g:form>
          </td>
      </tr>
  </table>
</body>
</html>
