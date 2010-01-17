<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Sign In</title>
        <g:javascript library="jquery"/>
        <g:javascript src="jquery/jquery-ui-1.7.2.custom.min.js"/>
        <g:javascript>
                $(function() {
                });
        </g:javascript>
        <link rel="stylesheet" href="${resource(dir:'css/redmond',file:'jquery-ui-1.7.2.custom.css')}" media="screen, projection" />
    </head>
<body>
    <div class="jquery-ui content">
        <table class="entryForms">
          <tr>
              <td class="ui-widget ui-corner-all signIn">
                  <g:form id="signIn" name="signIn" action="signIn">
                      <h1 class="ui-widget-header">Sign In</h1>
                      <g:if test="${flash.message}">
                        <div class="ui-widget ui-state-error ui-corner-all">
                            <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                            <strong>Alert:</strong> ${flash.message}</p>
                        </div>
                      </g:if>
                    <fieldset>
                        <label for="username">Email</label>
                        <input class="required" type="text" id="username" name="username" value="${username}" />
                    </fieldset>
                    <fieldset>
                        <label for="password">Password</label>
                        <input class="required" type="password" id="password" name="password" value="" />
                        <g:link action="resetPassword">Forgot your password?</g:link>
                    </fieldset>
                    <!--fieldset class="last">
                        <label class="rememberMe" for="rememberMe">Remember me?</label> <g:checkBox class="rememberMe" id="rememberMe" name="rememberMe" value="${rememberMe}" />
                    </fieldset-->
                    <input type="hidden" name="targetUri" value="${targetUri}" />
                    <a class="submit" onClick="document.signIn.submit();" id="submitbtn">Sign In &raquo;</a>
                  </g:form>
              </td>
              <!--td>
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
                    <a class="submit" onClick="document.register.submit();" id="registerbtn">Register &raquo;</a><-- or <g:link >Continue as guest</g:link>->
                  </g:form>
              </td-->
          </tr>
      </table>
      </div>
</body>
</html>
