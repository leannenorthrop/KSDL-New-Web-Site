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
  Template for displaying a single article along with related contents.

  User: Leanne Northrop
  Date: Feb 12, 2010, 8:32:39 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="grid_12">
  <div class="grid article">
    <g:if test="${!articleInstance.displayAuthor && !articleInstance.displayDate}">
    <div class="grid_16 body ${articleInstance?.category}">
    </g:if>
    <g:else>
      <div class="grid_4 ${articleInstance?.category}" style="overflow-x: hidden;">
        <g:if test="${articleInstance.datePublished}">
          <h4><g:formatDate format="dd MMMM, yyyy" date="${articleInstance.datePublished}"/></h4>
        </g:if>
        <g:if test="${articleInstance.displayAuthor}">
          <h5>by <a>${articleInstance.author.username}</a></h5>
        </g:if>
      </div>

      <div class="grid_12  ${articleInstance?.category}">
    </g:else>
    <g:if test="${articleInstance?.image && articleInstance?.image?.mimeType.endsWith('png')}">
      <img src="${createLink(controller: 'image', action: 'src', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}" class="pngImg"/>
    </g:if>
    <g:elseif test="${articleInstance?.image}">
      <img src="${createLink(controller: 'image', action: 'src', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
    </g:elseif>
      ${articleInstance.content.encodeAsTextile()}

      <p class="meta">
        <g:message code="article.labels"/> ${articleInstance?.tags?.join(", ")}
      </p>
      </div>
   </div>
</div>

<div class="grid_4">
  <h3><g:message code="similar"/></h3>
  <ul>
    <g:each in="${articles}" status="i" var="articleInstance">
      <li class="article"><g:link action="view" id="${articleInstance.id}">${articleInstance.title}</g:link></li>
    </g:each>
  </ul>
</div>
<div class="clear"></div>
