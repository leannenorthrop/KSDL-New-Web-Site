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

<%--
  User: Leanne Northrop
  Date: Jan 29, 2010,5:01:51 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>${title}</title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="grid_12">
        <g:render template="/eventlist" model="[events: todaysEvents, heading:'event.today']"/>
        <g:render template="/eventlist" model="[events: thisWeeksEvents,heading: 'event.this.week']"/>
        <g:render template="/eventlist" model="[events: thisMonthEvents,heading:'event.this.month']"/>
    </div>
    <div class="grid_4">
        <div class="box">
          <h2><g:message code="event.forthcoming"/></h2>
          <ul>
            <g:each var="date" in="${followingMonths}">
              <g:set var="start"><g:formatDate format="yyyy-MM-dd" date="${date[0]}"/></g:set>
              <g:set var="end"><g:formatDate format="yyyy-MM-dd" date="${date[1]}"/></g:set>
              <g:set var="label"><g:formatDate format="MMMM, yyyy" date="${date[0]}"/></g:set>
              <li><g:link controller="event" action="list" params="[sort:'title',order:'asc',start: start,end:end]">${label}</g:link></li>
            </g:each>
          </ul>
        </div>
        <g:render template="/shortEventsList" model="[events: regularEvents, heading:'event.regular']"/>
    </div>
  </body>
</html>
