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
  Template for displaying a list of events.
  User: Leanne Northrop
  Date: Feb 12, 2010, 7:34:02 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<h2><g:message code="${heading}"/></h2>
<ol>
  <g:each in="${events}" status="i" var="event">
    <g:set var="rule" value="${event?.dates[0]}"/>
    <g:if test="!rule?.isRule">
      <li class="group">
        <h3>${event?.title}</h3>
        <h4>${rule}<!--g:formatDate date="rule?.startDate}" type="date" style="LONG"/--> (<joda:format style="-S" date="${rule?.startTime?.toLocalTime()}"/> - <joda:format style="-S" date="${rule?.endTime?.toLocalTime()}"/> ${fieldValue(bean: rule, field: "duration")})</h4>
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
    </g:if>
    <g:elseif test="">

    </g:elseif>
    <g:else>

    </g:else>
  </g:each>
</ol>
