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
        <p>
            <g:if test="${user.profile?.image && user.profile?.mimeType?.endsWith('png')}">
              <img src="${createLink(controller: 'profile', action: 'src')}" title="${user.profile?.publicName}" alt="${user.profile?.publicName}" class="pngImg"/>
            </g:if>
            <g:else>
            ${createLink(controller: 'profile', action: 'src', id: 2)}
              <img src="${createLink(controller: 'profile', action: 'src', id: 2)}" title="${user.profile?.publicName}" alt="${user.profile?.publicName}"/>
            </g:else> 
            ${user.profile?.publicName} (${user.profile?.nickName}) 
            ${user.roles?.join(", ")}          
        </p>
    </body>
</html>