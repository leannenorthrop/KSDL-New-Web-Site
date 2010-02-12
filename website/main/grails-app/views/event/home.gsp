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
<%@ page import="org.joda.time.*" %>
<html>
  <head>
    <title>${title}</title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="col1_80_Percent">
      <div class="box">
        <h2><g:message code="event.today"/></h2>
        <g:render template="/eventlist" model="[events: todaysEvents]"/>
      </div>
    <div class="box">
        <h2><g:message code="event.this.week"/></h2>
        <g:render template="/eventlist" model="[events: thisWeeksEvents]"/>
      </div>
      <div class="box">
        <h2><g:message code="event.this.month"/></h2>
        <g:render template="/eventlist" model="[events: thisWeeksEvents]"/>
      </div>
    </div>
    <div class="col2_20_Percent">
      <div class="box">
        <h2><g:message code="event.regular"/></h2>
        <ol>
        </ol>
      </div>
    </div>
  </body>
</html>
