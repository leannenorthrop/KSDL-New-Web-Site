<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title><g:message code="log.in" default="Sign In"/></title>
    </head>
<body>
    <g:if test="${flash.message}">
      <p class="ui-widget ui-state-error ui-corner-all">
      <strong><span class="ui-icon ui-icon-alert" style="display: inline-block"></span><g:message code="alert"/></strong><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/></p>
    </g:if>
    <table class="entryForms">
      <tr>
          <td class="ui-widget ui-corner-all signIn">
              <g:form id="signIn" name="signIn" action="signIn">
                  <h1 class="ui-widget-header"><g:message code="log.in" default="Sign In"/></h1>
                <fieldset>
                    <label for="username"><g:message code="log.in.un"/></label>
                    <g:textField class="required ui-corner-all" name="username" value="${username}" />
                </fieldset>
                <fieldset>
                    <label for="password"><g:message code="log.in.pw"/></label>
                    <g:passwordField class="required ui-corner-all" name="password" value="${password}" /><br/>
                    <g:link action="resetPassword"><g:message code="passwd.reset.msg"/></g:link>
                </fieldset>
                <!--fieldset class="last">
                    <label class="rememberMe" for="rememberMe">Remember me?</label> <g:checkBox class="rememberMe" id="rememberMe" name="rememberMe" value="${rememberMe}" />
                </fieldset-->
                <g:hiddenField name="targetUri" value="${targetUri}" />
                <g:set var="submitBtnLabel"><g:message code="log.in.btn"/></g:set>
                <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
              </g:form>
          </td>
          <td class="middle">
              <g:message code="log.in.or"/>
          </td>
          <td class="ui-widget ui-corner-all register">
              <g:form id="register" name="register" action="register">
                <h1 class="ui-widget-header"><g:message code="register.title"/></h1>
                <fieldset>
                    <label for="username"><g:message code="log.in.un"/></label>
                    <g:textField name="username" value="${username}" class="required ui-corner-all" />
                </fieldset>
                <fieldset>
                    <label for="password"><g:message code="log.in.pw"/></label>
                    <g:passwordField name="password" value="" class="required ui-corner-all" />
                </fieldset>
                <fieldset>
                    <label for="passwordAgain"><g:message code="log.in.pw"/></label>
                    <g:passwordField name="passwordAgain" value="" class="required ui-corner-all" />
                </fieldset>
                <g:set var="submitBtnLabel"><g:message code="register.btn"/></g:set>
                <g:submitButton name="regsubmitbtn" value="${submitBtnLabel}" id="regsubmitbtn" class="ui-corner-all"/>
              </g:form>
          </td>
      </tr>
  </table>
</body>
</html>
