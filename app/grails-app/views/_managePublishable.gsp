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
  Template for managing publishable items to be used in conjunction with the
  template managePublishableJS in the header.
  User: Leanne Northrop
  Date: Feb 18, 2010, 12:59:34 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:if test="${params.max}">
  <g:set var="listMaxParam" value="?max=${params.max}&sort=title&order=asc"/>
</g:if>
<g:else>
  <g:set var="listMaxParam" value="?sort=title&order=asc"/>
</g:else>
<div id="${tabsId}">
  <ul>
    <shiro:hasAnyRole in="['Author','Administrator','Editor','VenueManager','EventOrganiser']">
    <li><a href="ajaxUnpublished${listMaxParam}"><g:message code="article.unpublished"/></a></li>
    </shiro:hasAnyRole>
    <li><a href="ajaxReady${listMaxParam}"><g:message code="article.ready"/></a></li>
    <li><a href="ajaxPublished${listMaxParam}"><g:message code="article.published"/></a></li>
    <li><a href="ajaxArchived${listMaxParam}"><g:message code="article.archived"/></a></li>
    <li><a href="ajaxDeleted${listMaxParam}"><g:message code="article.deleted"/></a></li>
  </ul>
</div>
