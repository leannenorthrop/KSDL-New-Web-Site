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
    <meta name="layout" content="content-admin" />
    <title><g:message code="profile.title" args="${[user.profile?.publicName]}"/></title>
  </head>
  <body>
    <form>
      <div class="container_16">
        <fieldset>
          <div class="grid_3">
            <g:if test="${user.profile?.image && user.profile?.mimeType?.endsWith('png')}">
              <img src="${createLink(controller: 'profile', action: 'src', id: user.id)}" title="${user.profile?.publicName}" alt="${user.profile?.publicName}" class="pngImg" style="max-width:100%"/>
            </g:if>
            <g:else>
              <img src="${createLink(controller: 'profile', action: 'src', id: user.id)}" title="${user.profile?.publicName}" alt="${user.profile?.publicName}" style="max-width:100%"/>
            </g:else>
          </div>
          <div class="grid_13">
            <p>
              <label>Public Name</label> ${user.profile?.publicName}
            </p>
            <p>
              <label>Nick Name</label> ${user.profile?.nickName}
            </p>
            <p>
              <label>Permissions</label> ${user.roles?.join(", ")}
            </p>
            <p class="last">
              <label>Last Logged In</label> ${user.profile?.lastLoggedIn.prettyDate()}
            </p>
            <g:actionSubmit value="${message(code:'profile.edit.btn')}" action="edit" class="ui-corner-all"/>
          </div>
        </fieldset>
      </div>
    </form>
  </body>
</html>