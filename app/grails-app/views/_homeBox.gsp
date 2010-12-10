%{------------------------------------------------------------------------------
  - Copyright ¬© 2010 Leanne Northrop
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
  - ‚ÄúSamye Content Management System‚Äù written by Leanne Northrop.
  ----------------------------------------------------------------------------}%

<%--
  Featured article list
  User: Leanne Northrop
  Date: Feb 12, 2010,7:36:15 PM
--%>

<%@ page import="org.samye.dzong.london.community.Article" contentType="text/html;charset=UTF-8" %>
<div class="box">
    <g:set var="articleInstance" value="${articles[0]}"/>
    <h3>
        <g:if test="${action}">
            <g:link controller="${controller}" action="${action}">${articleInstance?.title}</g:link>        
        </g:if>
        <g:else>
            <g:link controller="${controller}" action="index">${articleInstance?.title}</g:link>
        </g:else>
    </h3>

    <g:if test="${articleInstance?.image}">
        <g:if test="${action}">
            <g:link controller="${controller}" action="${action}"><img id="articleImage" src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/></g:link>
        </g:if>
        <g:else>
            <g:link controller="${controller}" action="index"><img id="articleImage" src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/></g:link>
        </g:else>    
    </g:if>
    
    ${articleInstance?.summary?.encodeAsTextile()}
    
    <g:if test="${articleInstance?.content}">
        <span class="more"><g:link controller="${controller}" action="view" id="${articleInstance.id}"><g:message code='content.more'/></g:link></span>
    </g:if>    
</div>
