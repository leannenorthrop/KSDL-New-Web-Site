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
        <div class="container_16">
            <div class="grid_3">
                <g:if test="${user.profile?.image && user.profile?.mimeType?.endsWith('png')}">
                  <img src="${createLink(controller: 'profile', action: 'src', id: user.id)}" title="${user.profile?.publicName}" alt="${user.profile?.publicName}" class="pngImg" style="max-width:100%"/>
                </g:if>
                <g:else>
                  <img src="${createLink(controller: 'profile', action: 'src', id: user.id)}" title="${user.profile?.publicName}" alt="${user.profile?.publicName}" style="max-width:100%"/>
                </g:else>                
            </div>
            <div class="grid_13">
                <h4>${user.profile?.publicName} (${user.profile?.nickName})</h4>
                <h5>${user.roles?.join(", ")}</h5>
                <h6>Last Logged In: <g:formatDate format="dd-MM-yyyy HH:mm" date="${user.profile?.lastUpdated}"/></h6>                
            </div>            
        </div>
    </body>
</html>