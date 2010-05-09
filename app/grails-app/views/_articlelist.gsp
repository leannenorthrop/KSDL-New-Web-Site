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

<%@ page import="org.samye.dzong.london.events.Event; org.samye.dzong.london.community.Teacher" contentType="text/html;charset=UTF-8" %>
<g:set var="typeClass" value="${articles[0] instanceof Teacher ? 'teacher' : (articles[0] instanceof Event ? 'event' : '')}"/>
<div class="box">
  <h2><g:message code="${heading}" default=""/></h2>
  <ul class="block articles">
    <g:if test="${articles}">

      <g:each in="${articles}" status="i" var="articleInstance">

        <g:set var="placementClass" value="${i == 0 ? 'first' : (i == articles.size ? 'last' :'')}"/>
        <li class="${placementClass} article ${typeClass} ${articleInstance?.category}">

          <g:if test="${!(articleInstance instanceof Teacher)}">
            <g:set var="articleHeading" value="${articleInstance.title}"/>
          </g:if>
          <g:else>
            <g:set var="articleHeading"><g:message code="${'teacher.title.' + articleInstance?.title}"/> ${articleInstance.name}</g:set>
          </g:else>

          <h3>
            <g:if test="${articleInstance.content}">
              <g:link controller="${controller}" action="${action}" id="${articleInstance.id}">${articleHeading}</g:link>
            </g:if>
            <g:else>
              ${articleHeading}
            </g:else>
          </h3>

          <g:if test="${articleInstance.displayAuthor || articleInstance.displayDate}">
            <p class="meta">
              <g:if test="${articleInstance.displayAuthor}">
                by <a>${articleInstance.author.username}</a>
              </g:if>
              <g:if test="${articleInstance.displayAuthor && articleInstance.displayDate}">
                --
              </g:if>
              <g:if test="${articleInstance.displayDate}">
                <g:formatDate format="dd MMMM, yyyy" date="${articleInstance?.datePublished}"/>
              </g:if>
            </p>
          </g:if>

          <g:if test="${articleInstance.image}">
            <a href="#" class="image">
              <img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
            </a>
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
  </ul>
  <g:if test="${total > articles.size()}">
    <g:link action="${moreAction}"><em><g:message code="articles.more"/></em></g:link>
  </g:if>
</div>
