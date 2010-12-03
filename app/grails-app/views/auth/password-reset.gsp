%{------------------------------------------------------------------------------
- Copyright © 2010 Leanne Northrop
-
- This file is part of Samye Content Management System.
    -
- Samye Content Management System is free software: you can redistribute it
- and/or modify it under the terms of the GNU General Public License as
- published by the Free Software Foundation, either version 3 of the License,
- or (at your option) any later version.
    -
- Samye Content Management System is distributed in the hope that it will be
- useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
- GNU General Public License for more details.
    -
- You should have received a copy of the GNU General Public License
- along with Samye Content Management System.
    - If not, see <http://www.gnu.org/licenses/>.
-
- BT plc, hereby disclaims all copyright interest in the program
- “Samye Content Management System” written by Leanne Northrop.
    ----------------------------------------------------------------------------}%
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="passwd.reset.request" default="Request Password Reset"/></title>
  </head>
  <body>
  <g:form id="resetpasswordform" name="resetpasswordform" action="onResetPassword" style="min-height: 17em">
    <fieldset>
      <legend><g:message code="passwd.reset.request" default="Request Password Reset"/></legend>

      <p><g:message code="passwd.reset.request.msg"/></p>

      <p>
        <label for="username"><g:message code="passwd.reset.request.lb"/></label>
      <g:textField name="username" value="${username}" style="width:20em;" class="ui-corner-all"/>
      </p>

      <p class="last">&nbsp;</p>
      <g:set var="submitBtnLabel"><g:message code="passwd.reset.submit"/></g:set>
      <g:submitButton name="resetPassword" value="${submitBtnLabel}" id="resetPassword" class="ui-corner-all"/>
    </fieldset>
  </g:form>
</body>
</html>

