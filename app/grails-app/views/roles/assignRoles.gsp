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
    <title>
      <g:message code='role.perm.title' default="Manage User Permissions"/></title>
    <meta name="layout" content="content-admin"/>
  </head>
  <body>
  <g:set var="minHeightEm" value="${5+(users.size()*3)}em;"/>
  <g:form controller="roles" id="roles" action="assignRoles" style="min-height:${minHeightEm}">
    <table>
      <thead>
        <tr>
          <th><g:message code='role.perm.username.col.title'/></th>
      <th><g:message code='role.perm.last.login.col.title'/></th>
      <g:each var="role" in="${roles}">
        <th><g:message code='role.${role.name}'/></th>
      </g:each>
      </tr>
      </thead>
      <tbody>
      <g:each in="${users}" status="i" var="user">
        <tr style="min-height:15em;">
          <td>${user.username}</td>
          <td>${user?.profile?.lastLoggedIn.prettyDate()}</td>
        <g:each var="role" in="${roles}">
          <g:set var="checkboxName" value="${user.id + '-' +role.name}"/>
          <g:set var="avalue" value="${Boolean.FALSE}"/>
          <g:findAll in="${user.roles}" expr="${it.name.contains(role.name)}">
            <g:set var="avalue" value="${Boolean.TRUE}"/>
          </g:findAll>
          <td><g:checkBox name="${checkboxName}" value="${avalue}"/></td>
        </g:each>
        </tr>
      </g:each>
      </tbody>
    </table>

    <g:set var="submitBtnLabel"><g:message code="role.perm.submit"/></g:set>
    <g:submitButton name="rolePermSubmitBtn" value="${submitBtnLabel}" id="requestRolesBtn" class="ui-corner-all"/>

  </g:form>
</body>
</html>
