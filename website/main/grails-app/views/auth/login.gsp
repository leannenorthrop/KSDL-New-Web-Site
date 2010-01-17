<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>Kagyu Samye Dzong London: Sign In</title>
        <meta name="layout" content="main" />
    </head>
<body>
  <table border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td>
              <g:form id="signIn" name="signIn" action="signIn">
                <h1>Sign In</h1>
                <g:if test="${flash.message}">
                  <label class="message">${flash.message}</label>
                </g:if>
                <fieldset>
                    <label for="username">Email</label>
                    <input type="text" id="username" name="username" value="${username}" />
                </fieldset>
                <fieldset>
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" value="" />
                    <g:link action="resetPassword">Forgot your password?</g:link>
                </fieldset>
                <fieldset class="last">
                    <label class="rememberMe" for="rememberMe">Remember me?</label> <g:checkBox class="rememberMe" id="rememberMe" name="rememberMe" value="${rememberMe}" />
                </fieldset>
                <input type="hidden" name="targetUri" value="${targetUri}" />
                <a class="submit" onClick="document.signIn.submit();" id="submitbtn">Sign In &raquo;</a>
              </g:form>
          </td>
          <td>
              or
          </td>
          <td>
              <g:form id="register" name="register" action="register">
                <h1>Become a Member</h1>
                <g:if test="${flash.message}">
                  <label class="message">${flash.message}</label>
                </g:if>
                <fieldset>
                    <label for="username">Email</label>
                    <input type="text" id="username" name="username" value="${username}" />
                </fieldset>
                <fieldset>
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" value="" />
                </fieldset>
                <fieldset>
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password2" value="" />
                </fieldset>
                <a class="submit" onClick="document.register.submit();" id="registerbtn">Register &raquo;</a><!-- or <g:link >Continue as guest</g:link>-->
              </g:form>
          </td>
      </tr>
  </table>
</body>
</html>
