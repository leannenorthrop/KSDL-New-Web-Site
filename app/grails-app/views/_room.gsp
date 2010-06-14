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
  Template for displaying room information
  User: Leanne Northrop
  Date: Jun 14, 2010, 3:51:06 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="bodyNoDetails group">
  <g:if test="${room?.image}">
    <g:if test="${room?.image?.mimeType.endsWith('png')}">
      <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: room.image.id)}" title="${room.image.name}" alt="${room.image.name}" class="pngImg" style="float:right"/>
    </g:if>
    <g:else>
      <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: room.image.id)}" title="${room.image.name}" alt="${room.image.name}" style="float:right"/>
    </g:else>
  </g:if>
<g:if test="${room?.content}">
  ${room?.content?.encodeAsTextile()}
</g:if>
<g:else>
  ${room?.summary?.encodeAsTextile()}
</g:else>
</div>
