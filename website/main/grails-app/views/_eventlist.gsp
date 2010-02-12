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
  Created by IntelliJ IDEA.
  User: northrl
  Date: Feb 12, 2010
  Time: 7:34:02 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<h2><g:message code="${heading}"/></h2>
<ol>
  <g:each in="${events}" status="i" var="event">
    <li class="group">
      <h3>${event?.title}</h3>
      <h4><joda:format style="M-" date="${event?.eventDate}"/> (<joda:format style="-S" date="${event?.startTime?.toLocalTime()}"/> ${fieldValue(bean: event, field: "eventDuration")})</h4>
      <h5><g:link controller="teacher" action="view" id="${event?.leader?.id}"><g:message code="teacher.title.${event?.leader?.title}"/> ${event?.leader?.name}</g:link></h5>

      <g:if test="${event.image}">
        <img src="${createLink(controller: 'image', action: 'thumbnail', id: event.image.id)}" title="${event.image.name}" alt="${event.image.name}"/>
      </g:if>
      <p>
        ${event.summary}
      </p>
      <p>
        <g:if test="${event.content}">
          <g:link controller="event" action="view" id="${event.id}"><g:message code='content.more'/></g:link>
        </g:if>
      </p>
    </li>
  </g:each>
</ol>
