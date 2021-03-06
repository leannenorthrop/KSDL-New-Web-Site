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
<%@ page import="org.joda.time.Minutes; org.joda.time.Hours; org.samye.dzong.london.events.Event;org.joda.time.*" %>
%{--
Edit Event template

Author: Leanne Northrop
Date: 30th January, 2010, 15:52
--}%
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="event.edit.title" args="${[event]}"/></title>
  </head>
  <body>
  <g:form name="editEvent" action="update" method="post">
    <g:hiddenField name="id" value="${event?.id}"/>
    <g:hiddenField name="version" value="${event?.version}"/>
    <g:hiddenField name="publishState" value="Unpublished"/>
    <g:hiddenField name="deleted" value="false"/>
    <g:hiddenField name="displayAuthor" value="false"/>
    <g:hiddenField name="displayDate" value="false"/>
    <g:hiddenField name="isRepeatable" value="false"/>
    <g:hiddenField name="home" value="false"/>
    <g:hiddenField name="featured" value="false"/>

    <fieldset>
      <g:render template="/editEvent" model="[event: event]"/>

      <g:submitButton name="create" class="ui-corner-all" value="${message(code: 'event.save.btn', default: 'Save Changes')}"/>
    </fieldset>
  </g:form>
</body>
</html>


