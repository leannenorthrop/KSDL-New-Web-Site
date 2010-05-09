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
  Featured article list
  User: Leanne Northrop
  Date: Feb 12, 2010,7:36:15 PM
--%>

<%@ page import="org.samye.dzong.london.community.Teacher; org.samye.dzong.london.community.Article" contentType="text/html;charset=UTF-8" %>
  <g:each in="${articles}" status="i" var="articleInstance">
    <div class="box article">
      <g:if test="${!(articleInstance instanceof Teacher)}">
        <h2>${articleInstance?.title}</h2>
      </g:if>
      <g:else>
        <h2><g:message code="${'teacher.title.' + articleInstance?.title}"/> ${articleInstance.name}</h2>
      </g:else>

      <g:if test="${articleInstance?.image}">
        <g:if test="${articleInstance?.image?.mimeType.endsWith('png')}">
          <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}" class="pngImg"/>
        </g:if>
        <g:else>
          <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
        </g:else>
      </g:if>
      <p>
        ${articleInstance?.summary}
      </p>
      <p>
        <g:if test="${articleInstance?.content && !(articleInstance instanceof Teacher)}">
          <g:link action="view" id="${articleInstance.id}"><g:message code='content.more'/></g:link>
        </g:if>
        <g:elseif test="${articleInstance?.content && (articleInstance instanceof Teacher)}">
          <g:link controller="teacher" action="view" id="${articleInstance.id}"><g:message code='content.more'/></g:link>
        </g:elseif>
      </p>
    </div>
  </g:each>
