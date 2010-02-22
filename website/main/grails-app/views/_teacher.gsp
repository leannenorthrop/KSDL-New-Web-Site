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
  Template for displaying teacher information
  User: Leanne Northrop
  Date: Feb 19, 2010,1:31:06 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="grid_12">
      <h2><g:message code="${'teacher.title.' + teacher?.title}"/> ${teacher.name}</h2>

<g:if test="${teacher.displayAuthor || teacher.displayDate}">
  <ul>
    <g:if test="${teacher.image}">
      <li><img src="${createLink(controller: 'image', action: 'thumbnail', id: teacher.image.id)}" title="${teacher.image.name}" alt="${teacher.image.name}"/></li>
    </g:if>
    <g:if test="${teacher.displayDate && teacher.datePublished}">
      <li><h3><span class="date"><g:formatDate format="dd MMMM, yyyy" date="${teacher.datePublished}"/></span></h3></li>
    </g:if>
    <g:if test="${teacher.displayAuthor}">
      <li><h4>by <a>${teacher.author.username}</a></h4></li>
    </g:if>
  </ul>
</g:if>

<g:if test="${!teacher.displayAuthor && !teacher.displayDate}">
  <div class="bodyNoDetails group">
  <g:if test="${teacher?.image}">
    <g:if test="${teacher?.image?.mimeType.endsWith('png')}">
      <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: teacher.image.id)}" title="${teacher.image.name}" alt="${teacher.image.name}" class="pngImg" style="float:right"/>
    </g:if>
    <g:else>
      <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: teacher.image.id)}" title="${teacher.image.name}" alt="${teacher.image.name}" style="float:right"/>
    </g:else>
  </g:if>
</g:if>
<g:if test="${teacher.displayAuthor || teacher.displayDate}">
  <div class="body group">
</g:if>
<g:if test="${teacher.content}">
  ${teacher.content.encodeAsTextile()}
</g:if>
<g:else>
  ${teacher.summary.encodeAsTextile()}
</g:else>
</div><!-- /body -->
</div><!-- /left -->

<div class="grid_4">
  <div class="box">
    <h2><g:message code="events.label"/></h2>
    <ul>
      <g:each in="${events}" status="i" var="event">
        <li class="event"><g:link controller="event" action="view" id="${event.id}">${event.title}</g:link></li>
      </g:each>
    </ul>
  </div>
  <div class="box">
    <h2><g:message code="similar"/></h2>
    <ul>
      <g:each in="${articles}" status="i" var="article">
        <li class="article"><g:link controller="aboutUs" action="view" id="${article.id}">${article.title}</g:link></li>
      </g:each>
    </ul>
  </div>
</div>

