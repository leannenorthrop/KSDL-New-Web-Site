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
  Display news article.
  User: Leanne
  Date: Jan 24, 2010, 2:00:21 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <title>${articleInstance.title}</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <div class="col1_80_Percent article">
            <h2>${articleInstance.title}</h2>

            <g:if test="${articleInstance.displayAuthor || articleInstance.displayDate}">
            <ul>
                <g:if test="${articleInstance.image}">
                    <li><img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/></li>
                </g:if>
                <g:if test="${articleInstance.displayDate && articleInstance.publishedOn}">
                    <li><h3><span class="date"><g:formatDate format="dd MMMM, yyyy" date="${articleInstance.publishedOn}"/></span></h3></li>
                </g:if>
                <g:if test="${articleInstance.displayAuthor}">
                    <li><h4>by <a>${articleInstance.author.username}</a></h4></li>
                </g:if>
            </ul>
            </g:if>

            <g:if test="${!articleInstance.displayAuthor && !articleInstance.displayDate}">
                <div class="bodyNoDetails group">
                    <g:if test="${articleInstance.image}">
                    <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
                    </g:if>
            </g:if>
            <g:if test="${articleInstance.displayAuthor || articleInstance.displayDate}">
                <div class="body group">
            </g:if>
                ${articleInstance.content.encodeAsTextile()}
            </div><!-- /body -->
        </div><!-- /left -->

        <div class="col2_20_Percent">
            <h2><g:message code="news.similar"/></h2>
            <ul>
                <g:each in="${articles}" status="i" var="articleInstance">
                <li class="article">
                    <h3><g:link action="view" id="${articleInstance.id}">${articleInstance.title}</g:link></h3>
                    <h4>by <a>${articleInstance.author.username}</a></h4>
                    <g:if test="${articleInstance.displayDate}">
                        <h5><g:formatDate format="dd MMMM, yyyy" date="${articleInstance.displayDate}"/></h5>
                    </g:if>
                    <g:if test="${articleInstance.image}">
                        <img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}" />
                    </g:if>
                </li>
                </g:each>
            </ul>
        </div>
    </body>
</html>
