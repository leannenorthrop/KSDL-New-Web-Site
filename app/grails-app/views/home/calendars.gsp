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
    <title><g:message code="calendars.title"/></title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="grid_2">&nbsp;</div>
    <div class="grid_12">
      <h3><g:message code="calendars.title"/></h3>
      <p><g:message code="calendars.msg"/>
      <ul class="calendar">
        <g:each in="${['all','meditation','wellbeing','community','buddhism']}" var="feed">
          <li class="calendar">
            <g:set var="a">
              <g:link controller="event" action="subscribe" params="${[type:feed]}"><g:message code="${feed}"/></g:link>
            </g:set>
            <g:message code="${'calendars.'+feed}" args="${[a]}"/>
          </li>
        </g:each>
      </ul>
    </p>
    </div>
    <div class="grid_2">&nbsp;</div>
  </body>
</html>
