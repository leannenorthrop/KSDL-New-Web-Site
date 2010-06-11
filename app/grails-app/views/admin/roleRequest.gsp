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
        <title><g:message code='req.perm.title'/></title>
        <meta name="layout" content="content-admin" />
    </head>
<body>
  <g:form name="requestRoles" controller="admin" action="requestRoles" class="ui-widget ui-corner-all" style="height: ${roles.size()*2}em">
    <g:hiddenField name="id" value="${user?.passwordReset}" />
    <g:hiddenField type="hidden" name="reset" value="${user?.passwordReset}" />

    <fieldset>
        <legend><g:message code='req.perm.msg'/></legend>
        
        <ul>
            <g:each var="role" in="${roles}">
                <li><g:checkBox name="${'role.' + role.name}" /><g:message code='role.${role.name}'/></li>
            </g:each>
        </ul>
        <p class="last">&nbsp;</p>
        <g:set var="submitBtnLabel"><g:message code="req.perm.submit"/></g:set>
        <g:submitButton name="requestRolesBtn" value="${submitBtnLabel}" id="requestRolesBtn" class="ui-corner-all"/>
    </fieldset>
  </g:form>
</body>
</html>
