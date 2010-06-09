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
    <title><g:message code="theme.select.title"/></title>
</head>
<body>
    <div class="grid_16">
        <g:set var="cssThemesDir" value="${new File(application.getRealPath('/css/themes')).listFiles()}" />
        <h1>Change Theme</h1>
        <p><g:if test="${cookie(name:'cssTheme')}">You are currently viewing <g:cookie name="cssTheme" />.</g:if> You can change the way this website looks by clicking any of the links below:<br/><br/>
            <ul>
            <g:each in="${cssThemesDir}">
                <g:if test="${it.isDirectory()}">
                    <g:set var="cssThemeName" value="${it.name}"/>
                    <li><g:link action="setCSSTheme" id="${cssThemeName}">${cssThemeName}</g:link></li>
                </g:if>
            </g:each>
            </ul>
        </p>
    </div>
</body>
</html>