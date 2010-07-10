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
  User: Leanne Northrop
  Date: Feb 27, 2010, 5:07:05 PM
--%>

<%@ page import="org.samye.dzong.london.events.Event; org.samye.dzong.london.community.Teacher" contentType="text/html;charset=UTF-8" %>
<g:set var="typeClass" value="${articles[0] instanceof Teacher ? 'teacher' : (articles[0] instanceof Event ? 'event' : '')}"/>

<g:if test="${articles}">
    <g:set var="articleInstance" value="${articles[0]}"/>
    <g:set var="articleHeading" value="${articleInstance.title}"/>

    <div class="${controller}">
    <h3><g:link controller="${controller}" action="${action}" id="${articleInstance.id}">${articleHeading}</g:link></h3>
    <g:if test="${articleInstance.image}">
        <g:set var="iurl" value="${createLink(controller: 'image', action: 'src', id: articleInstance.image.id)}"/>
        <img src="${iurl}"/>
    </g:if>
    <p style="display:hidden;">
        <g:link controller="${controller}" action="${action}" id="${articleInstance.id}">
        ${articleInstance?.summary?.encodeAsTextile()}
        </g:link>
    </p>
    </div>
</g:if>

