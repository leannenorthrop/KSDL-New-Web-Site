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
  Template for display an event and related items.

  User: Leanne Northrop
  Date: Feb 12, 2010,9:53:27 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:set var="rule" value="${event?.dates()[0]}"/>
<div class="col1_80_Percent event ${fieldValue(bean: event, field: "category")}">
  <h2>${event?.title}</h2>
  <h3><joda:format style="M-" date="${rule?.startDate}"/> (<joda:format style="-S" date="${rule?.startTime?.toLocalTime()}"/> ${fieldValue(bean: rule, field: "duration")})</h3>
  <h4><g:message code="teacher.title.${event?.leader?.title}"/> ${event?.leader?.name}</h4>
  <div class="body">
    <g:if test="${event?.image}">
      <g:if test="${event?.image?.mimeType.endsWith('png')}">
        <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: event.image.id)}" title="${event.image.name}" alt="${event.image.name}" class="pngImg" style="float:right"/>
      </g:if>
      <g:else>
        <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: event.image.id)}" title="${event.image.name}" alt="${event.image.name}" style="float:right"/>
      </g:else>
    </g:if>
    ${event.content.encodeAsTextile()}
  </div><!-- /body -->
  <p>
    Posted: <g:formatDate format="dd-MM-yyyy HH:mm" date="${event?.datePublished}"/>
    Last Updated: <g:formatDate format="dd-MM-yyyy HH:mm" date="${event?.lastUpdated}"/>
  </p>
</div><!-- /left -->

<div class="col2_20_Percent">
</div>
