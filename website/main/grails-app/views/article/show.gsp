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
    <title>${articleInstance?.title}</title>
    <meta name="layout" content="content-admin"/>
  </head>
  <body>
    <div class="col1_80_Percent article">
      <h2>${articleInstance?.title}</h2>

      <ul>
        <g:if test="${articleInstance?.image}">
          <li><img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance?.image.id)}" title="${articleInstance?.image.name}" alt="${articleInstance?.image.name}"/></li>
        </g:if>
        <g:if test="${articleInstance?.datePublished != null}">
          <li><h3><span class="date"><g:formatDate format="dd MMMM, yyyy" date="${articleInstance?.datePublished}"/></span></h3></li>
        </g:if>
        <li><h4>by <a>${articleInstance?.author.username}</a></h4></li>
      </ul>

      <div class="body">
        ${articleInstance.content.encodeAsTextile()}
      </div><!-- /body -->
    </div><!-- /left -->

    <div class="col2_20_Percent">
    </div>
  </body>
</html>
