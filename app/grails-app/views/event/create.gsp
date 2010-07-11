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
<joda:datePicker name="eventDate" precision="day" value="${event?.eventDate}" noSelection="['': '']" class="ui-corner-all" years="${2010..2050}"/>

  ----------------------------------------------------------------------------}%
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.joda.time.Minutes; org.joda.time.Hours; org.samye.dzong.london.events.Event;org.joda.time.*" %>
%{--
  Create Event template

  Author: Leanne Northrop
  Date: 29th January, 2010, 17:54
--}%
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="event.create.title"/></title>
    <jq:jquery>
      var container = $("#jserrors");
      $("#addevent").validate({
        onfocusout: false,
		errorContainer: container,
		errorLabelContainer: $("ol", container),
		wrapper: 'li'
	});
    </jq:jquery>
  </head>
  <body>
    <g:form name="addevent" action="save" method="post">
        <g:hiddenField name="publishState" value="Unpublished"/>
        <g:hiddenField name="deleted" value="false"/>
        <g:hiddenField name="displayAuthor" value="false"/>
        <g:hiddenField name="displayDate" value="false"/>
        <g:hiddenField name="isRepeatable" value="false"/>
        <g:hiddenField name="home" value="false"/>
        <g:hiddenField name="featured" value="false"/>

        <g:render template="/messageBox" model="[flash: flash]"/>            
        
        <g:render template="/editEvent" model="[event: event, rule:rule]"/>

        <p class="last">&nbsp;</p>
        <g:submitButton name="create" class="ui-corner-all" value="${message(code: 'event.add.btn', default: 'Add event')}"/>
    </g:form>
  </body>
</html>


