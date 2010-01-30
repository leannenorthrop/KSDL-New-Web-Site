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
    <title><g:message code="passwd.reset.request" default="Request Password Reset"/></title>
    <meta name="layout" content="content-admin"/>
  </head>
  <body>
    <g:form id="resetpasswordform" name="resetpasswordform" action="onResetPassword" class="ui-widget ui-corner-all" style="min-height: 17em">
      <h1 class="ui-widget-header"><g:message code="passwd.reset.request" default="Request Password Reset"/></h1>
      <div>
        <p><g:message code="passwd.reset.request.msg"/></p>
      </div>

      <fieldset>
        <label for="username"><g:message code="passwd.reset.request.lb"/></label>
        <g:textField name="username" value="${username}" style="width:20em;" class="ui-corner-all"/>
      </fieldset>
      <g:if test="${flash.message}">
        <p class="ui-widget ui-state-error ui-corner-all">
          <g:if test="${!flash.isError}">
            <strong><span class="ui-icon ui-icon-info" style="display:inline-block;"></span><g:message code="info"/></strong>
          </g:if>
          <g:else>
            <strong><span class="ui-icon ui-icon-alert" style="display:inline-block;"></span><g:message code="alert"/></strong>
          </g:else>
          <g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/></p>
      </g:if>
      <g:set var="submitBtnLabel"><g:message code="passwd.reset.submit"/></g:set>
      <g:submitButton name="resetPassword" value="${submitBtnLabel}" id="resetPassword" class="ui-corner-all"/>
    </g:form>
  </body>
</html>

