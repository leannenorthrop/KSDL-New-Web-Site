<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>Kagyu Samye Dzong London: Sign In</title>
        <meta name="layout" content="main" />
    </head>
<body>
    <h1>No worries! Just enter the e-mail address you used when creating your account. We'll send you an e-mail with a link to reset your password.</h1>
    <g:form id="resetPassword" name="resetPassword" action="onResetPassword">
        <h1>Sign In</h1>
        <g:if test="${flash.message}">
          <label class="message">${flash.message}</label>
        </g:if>
        <fieldset>
            <label for="username">Your Email Address</label>
            <input type="text" id="username" name="username" value="${username}" />
        </fieldset>
        <a class="submit" onClick="document.resetPassword.submit();" id="resetPassword">Reset Password &raquo;</a>
      </g:form>
</body>
</html>

