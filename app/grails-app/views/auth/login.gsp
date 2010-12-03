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
    <title><g:message code="log.in.title" default="Sign In"/></title>
  </head>
  <body>
  <g:form id="signIn" name="signIn" action="signIn">
    <g:hiddenField name="targetUri" value="${targetUri}"/>
    <fieldset>
      <legend><g:message code="log.in" default="Sign In"/></legend>
      <p>
        <label for="username"><g:message code="log.in.un"/></label>
      <g:textField class="required ui-corner-all" name="username" value="${username}"/>
      </p>
      <p>
        <label for="password"><g:message code="log.in.pw"/></label>
      <g:passwordField class="required ui-corner-all" name="password" value="${password}"/><br/>
      </p>
      <!--fieldset class="last">
   <label class="rememberMe" for="rememberMe">Remember me?</label> <g:checkBox class="rememberMe" id="rememberMe" name="rememberMe" value="${rememberMe}"/>
      </fieldset-->
      <p class="last">&nbsp;</p>
      <g:set var="submitBtnLabel"><g:message code="log.in.btn"/></g:set>
      <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
      <g:set var="btnLabel"><g:message code="passwd.reset.msg"/></g:set>
      <g:actionSubmit value="${btnLabel}" action="resetPassword" class="ui-corner-all"/>
    </fieldset>
  </g:form>
</body>
</html>
