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
<%@ page import="org.samye.dzong.london.media.Image;org.samye.dzong.london.venue.Venue" %>
<html>
  <head>
    <title><g:message code='settings.title' default="Manage Settings"/></title>
    <meta name="layout" content="content-admin"/>
  </head>
  <body>
  <g:form name="settings" action="save">
    <table>
      <thead>
        <tr>
          <th><g:message code='settings.name.label'/></th>
      <th><g:message code='settings.value.label'/></th>
      </tr>
      </thead>
      <tbody>
      <g:findAll in="${settings}" expr="(!it.name.startsWith('SS') && !it.name.startsWith('DefaultTheme') && !it.name.startsWith('ShopMenu') )">
        <tr style="min-height:15em;">
          <td><g:message code='settings.${it.name}'/></td>
        <g:if test="${it.name == 'Logo'}">
          <td>
          <g:select name="settings.${it.name}"
                    from="${Image.list()}"
                    value="${it.value}"
                    optionKey="id" />
          </td>
        </g:if>
        <g:elseif test="${it.name == 'DefaultVenue'}">
          <td>
          <g:select name="settings.${it.name}"
                    from="${Venue.list()}"
                    value="${it.value}"
                    optionKey="id" />
          </td>
        </g:elseif>
        <g:elseif test="${it.name == 'SiteMessage'}">
          <td><g:textArea name="settings.${it.name}" value="${it.value}" rows="8" cols="40"/></td>
        </g:elseif>
        <g:elseif test="${it.name.startsWith('Show')}">
          <td><g:select name="settings.${it.name}"
                        from="${[message(code:'true'),message(code:'false')]}"
                        value="${message(code:it.value)}"/></td>
        </g:elseif>
        <g:else>
          <td><g:textField name="settings.${it.name}" value="${it.value}" /></td>
        </g:else>
        </tr>
      </g:findAll>
      </tbody>
    </table>
    <p class="last">&nbsp;</p>
    <g:set var="submitBtnLabel"><g:message code="role.perm.submit"/></g:set>
    <g:submitButton name="rolePermSubmitBtn" value="${submitBtnLabel}" id="requestRolesBtn" class="ui-corner-all"/>

  </g:form>
</body>
</html>
