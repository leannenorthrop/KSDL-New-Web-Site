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
Display Room Hire article
User: Leanne Northrop
Date: Feb 19, 2010,2:44:11 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
  <head>
    <title>${article?.title}</title>
    <meta name="layout" content="main" />
  </head>
  <body>
    <div class="grid_12">
      <div class="grid article">
        <g:if test="${!article?.displayAuthor && !article?.displayDate}">
          <div class="grid_16 body ${article?.category}">
        </g:if>
        <g:else>
          <div class="grid_4 ${article?.category}" style="overflow-x: hidden;">
            <g:if test="${article?.datePublished}">
              <h4><g:formatDate format="dd MMMM, yyyy" date="${article.datePublished}"/></h4>
            </g:if>
            <g:if test="${article?.displayAuthor}">
              <h5>by <a>${article.author.username}</a></h5>
            </g:if>
          </div>

          <div class="grid_12 body ${article?.category}">
        </g:else>
        <g:if test="${article?.image && article?.image?.mimeType.endsWith('png')}">
          <img src="${createLink(controller: 'image', action: 'src', id: article.image.id)}" title="${article.image.name}" alt="${article.image.name}" class="pngImg"/>
        </g:if>
        <g:elseif test="${article?.image}">
          <img src="${createLink(controller: 'image', action: 'src', id: article.image.id)}" title="${article.image.name}" alt="${article.image.name}"/>
        </g:elseif>
${article?.content?.encodeAsTextile()}

        <p class="meta">
        <g:message code="article.labels"/> ${article?.tags?.join(", ")}
        </p>
      </div>
    </div>
  </div>

  <div class="grid_4">
    <g:render template="/venueMenu" model="[venues:venues]"/>
  </div>
  <div class="clear"></div>

</body>
</html>
