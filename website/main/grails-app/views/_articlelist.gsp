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
  Time: 8:35:05 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<h2><g:message code="${heading}"/></h2>
<ol>
  <g:if test="${articles}">
    <g:each in="${articles}" status="i" var="articleInstance">
      <li class="group">
        <h3>${articleInstance.title}</h3>
        <g:if test="${articleInstance.displayAuthor}">
          <h4>by <a>${articleInstance.author.username}</a></h4>
        </g:if>
        <g:if test="${articleInstance.displayDate}">
          <h5><g:formatDate format="dd MMMM, yyyy" date="${articleInstance?.datePublished}"/></h5>
        </g:if>
        <g:if test="${articleInstance.image}">
          <img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
        </g:if>
        <p>
          ${articleInstance.summary}
        </p>
        <p>
          <g:if test="${articleInstance.content}">
            <g:link controller="${controller}" action="${action}" id="${articleInstance.id}"><g:message code='content.more'/></g:link>
          </g:if>
        </p>
      </li>
    </g:each>
  </g:if>
</ol>
<g:if test="${total > articles.size()}">
  <g:link action="current"><em><g:message code="content.more"/></em></g:link>
</g:if>