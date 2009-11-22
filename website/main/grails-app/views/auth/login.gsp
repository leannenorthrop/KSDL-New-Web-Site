<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>Kagyu Samye Dzong London: Sign In</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <!--[if gte IE 7]-->
            <link rel="stylesheet" media="screen, projection" href="${resource(dir:'css/themes/default',file:'screen.css')}" />
        <!--[endif]-->

        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <lsd:link obj="${application}"/>
        <g:javascript library="application" />
    </head>
<body>
  <lsdc:header />

  <lsdc:nav />

  <lsdc:grid />

  <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
  </g:if>

  <div id="auth">
      <g:form name="signIn" action="signIn">
        <h1>Sign In</h1>
        <fieldset>
            <label for="username">Email</label>
            <input type="text" id="username" name="username" value="${username}" />
        </fieldset>
        <fieldset>
            <label for="password">Password</label>
            <input type="password" id="password" name="password" value="" />
        </fieldset>
        <fieldset class="last">
            <label class="rememberMe" for="rememberMe">Remember me?</label> <g:checkBox class="rememberMe" id="rememberMe" name="rememberMe" value="${rememberMe}" />
        </fieldset>
        <input type="hidden" name="targetUri" value="${targetUri}" />
        <a class="submit" onClick="document.signIn.submit();">Sign In &raquo;</a>
      </g:form>
      <g:form name="register" action="register">
        <h1>Become a Member</h1>
        You will be able to:
        <ul>
            <li>&nbsp;</li>
            <li>&nbsp;</li>
            <li>&nbsp;</li>
            <li>&nbsp;</li>
            <li>&nbsp;</li>
            <li>&nbsp;</li>
            <li>&nbsp;</li>
            <li>&nbsp;</li>
        </ul>
        <a class="submit" onClick="document.register.submit();">Register &raquo;</a> or <g:link >Continue as guest</g:link>
      </g:form>
  </div>
</body>
</html>
