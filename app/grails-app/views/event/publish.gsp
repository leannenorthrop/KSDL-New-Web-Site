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
  Prepare event for publication.
  User: Leanne Northrop
  Date: Jan 30, 2010, 1:30:09 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="event.publish.title"/></title>
    <jq:jquery>
      var container = $("#jserrors");
      $("#publishEvent").validate({
		errorContainer: container,
		errorLabelContainer: $("ol", container),
		wrapper: 'li'
	});
    </jq:jquery>
  </head>
  <body>
    <g:form name="publishEvent" action="publish" method="post">
        <g:hiddenField name="id" value="${event?.id}"/>
        <g:hiddenField name="version" value="${event?.version}"/>
        <g:hiddenField name="publishState" value="Unpublished"/>
        <g:hiddenField name="deleted" value="false"/>
        <g:hiddenField name="displayAuthor" value="false"/>
        <g:hiddenField name="displayDate" value="true"/>
        <g:hiddenField name="isRepeatable" value="false"/>
        <g:hiddenField name="home" value="false"/>
        <g:hiddenField name="featured" value="false"/>
        
        <fieldset>    
          <g:render template="/messageBox" model="[flash: flash]"/>

          <g:render template="/editEvent" model="[event: event,showPublication:Boolean.TRUE]"/>

          <p class="last">&nbsp;</p>
          <g:submitButton name="create" class="ui-corner-all" value="${message(code: 'event.publish.btn', default: 'Publish')}"/>
        </fieldset>
    </g:form>
  </body>
</html>


