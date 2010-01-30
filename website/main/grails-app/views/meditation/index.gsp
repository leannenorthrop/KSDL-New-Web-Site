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
  All meditation articles
  User: Leanne
  Date: Jan 24, 2010, 2:00:21 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title><g:message code="meditation.all.articles.title"/></title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="col1_80_Percent">
      <ol class="box">
        <g:each in="${topArticles}" status="i" var="articleInstance">
          <li>
            <h2>${articleInstance.title}</h2>
            <p>${articleInstance.summary.encodeAsTextile()} <g:link action="view" id="${articleInstance.id}"><g:message code="content.more"/></g:link></p>
          </li>
        </g:each>
      </ol>
    </div>
    <div class="col2_20_Percent">
      <div class="box">
        <h2>Video</h2>
      </div>
      <div class="box">
        <h2>Resources</h2>
        <ol>
          <li><g:link action="all">Articles</g:link></li>
        </ol>
      </div>
    </div>
    <div class="col1_33_Percent">
      <div class="articles box">
        <ol>
          <g:each in="${adviceArticles}" status="i" var="articleInstance">
            <li>
              <h2>${articleInstance.title}</h2>
              <g:if test="${articleInstance.image}">
                <img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
              </g:if>
              <p>${articleInstance.summary.encodeAsTextile()} <g:link action="view" id="${articleInstance.id}"><g:message code="content.more"/></g:link></p>
            </li>
          </g:each>
        </ol>
      </div>
      <div class="articles box">
        <ol>
          <g:each in="${benefitsArticles}" status="i" var="articleInstance">
            <li>
              <h3>${articleInstance.title}</h3>
              <g:if test="${articleInstance.image}">
                <img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
              </g:if>
              <p>${articleInstance.summary.encodeAsTextile()} <g:link action="view" id="${articleInstance.id}"><g:message code="content.more"/></g:link></p>
            </li>
          </g:each>
        </ol>
      </div>
    </div>
    <div class="col2_66_Percent">
      <div class="box">
        <h2><g:message code="event.meditation"/></h2>
        <ol>
          <g:each in="${events}" var="event">
            <li class="article group">
              <g:set var="t" value="${event.title}"/>
              <h3><g:link controller="event" action="view" id="${event?.id}">${t}</g:link></h3>
              <h4>by <g:link controller="teacher" action="view" id="${event?.leader?.id}">${event?.leader.name}</g:link></h4>
              <h5><joda:format style="M-" date="${event?.eventDate}"/> <joda:format style="-M" date="${event?.startTime}"/></h5>
              <g:if test="${event.image}">
                <img src="${createLink(controller: 'image', action: 'thumbnail', id: event.image.id)}" title="${event.image.name}" alt="${event.image.name}" style="float:left;"/>
              </g:if>
              <p>${event.summary}</p>
            </li>
          </g:each>
        </ol>
      </div>
    </div>
  </body>
</html>
