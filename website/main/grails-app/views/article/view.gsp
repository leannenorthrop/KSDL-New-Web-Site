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

<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
  <head>
    <title>${articleInstance.title}</title>
    <meta name="layout" content="main"/>
  </head>
  <body>
    <div class="col1_80_Percent article">
      <h2>${articleInstance?.title}</h2>

      <g:if test="${articleInstance?.displayAuthor || articleInstance?.displayDate}">
        <ul>
          <g:if test="${articleInstance?.image}">
            <li><img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance?.image.id)}" title="${articleInstance?.image.name}" alt="${articleInstance?.image.name}"/></li>
          </g:if>
          <g:if test="${articleInstance?.displayDate && articleInstance?.datePublished}">
            <li><h3><span class="date"><g:formatDate format="dd MMMM, yyyy" date="${articleInstance?.datePublished}"/></span></h3></li>
          </g:if>
          <g:if test="${articleInstance?.displayAuthor}">
            <li><h4>by <a>${articleInstance?.author?.username}</a></h4></li>
          </g:if>
        </ul>
      </g:if>

      <g:if test="${!articleInstance?.displayAuthor && !articleInstance?.displayDate}">
        <div class="bodyNoDetails group">
        <g:if test="${articleInstance?.image}">
          <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
        </g:if>
      </g:if>
      <g:if test="${articleInstance?.displayAuthor || articleInstance?.displayDate}">
        <div class="body group">
      </g:if>
      ${articleInstance?.content.encodeAsTextile()}
      <p class="labels"><g:message code="article.tag.label"/> ${articleInstance?.tags.join(', ')}</p>
    </div><!-- /body -->
    </div><!-- /left -->

      <div class="col2_20_Percent">
        <h2>Other Articles</h2>
        <ul>
          <li>Previous (To Do)</li>
          <li>Next (To Do)</li>
        </ul>
      </div>
  </body>
</html>
