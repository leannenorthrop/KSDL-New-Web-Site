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

<%@ page import="org.samye.dzong.london.events.Event" %>
<html>
  <head>
    <title>${title}</title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="grid_12">
        <g:render template="/eventlist" model="[events: events]"/>
    </div>
    <div class="grid_4">
        <div class="box">
          <h3><g:message code="event.forthcoming"/></h3>
          <ul>
            <g:each var="date" in="${followingMonths}">
              <g:set var="start"><g:formatDate format="yyyy-MM-dd" date="${date[0]}"/></g:set>
              <g:set var="end"><g:formatDate format="yyyy-MM-dd" date="${date[1]}"/></g:set>
              <g:set var="label"><g:formatDate format="MMMM, yyyy" date="${date[0]}"/></g:set>
              <li><g:link controller="event" action="list" params="[sort:'title',order:'asc',start: start,end:end]">${label}</g:link></li>
            </g:each>
          </ul>
        </div>
    </div>
  </body>
</html>
