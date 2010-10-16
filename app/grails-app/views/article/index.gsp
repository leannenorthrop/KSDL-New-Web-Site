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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
  <head>
    <meta name="layout" content="main"/>
    <title>${title}</title>
  </head>
  <body>
    <div class="articles">
      <ol>
        <g:each in="${articleInstanceList}" status="i" var="articleInstance">
          <li class="article">
            <h3><g:link action="view" id="${articleInstance.id}">${articleInstance.title}</g:link></h3>
            <h3>by <a>${articleInstance.author.username}</a></h3>
            <g:if test="${articleInstance?.datePublished && article.isDisplayDate}">
              <g:set var="publishDate"><span class="pretty-date">${articleInstance?.datePublished.prettyDate()}</span></g:set>
              <h4><g:message code="article.meta.published.on" args="${[publishDate]}"/></h4>
            </g:if>
            <p>${articleInstance.summary.encodeAsTextile()}</p>
          </li>
        </g:each>
    </div>
  </body>
</html>
