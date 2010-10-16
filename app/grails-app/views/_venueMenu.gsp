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
  Template for display an event and related items.

  User: Leanne Northrop
  Date: Feb 12, 2010,9:53:27 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<h3><g:message code="venues.heading"/></h3>
<ul class="menu">
  <li class="menuitem">
      <ul>
      <g:if test="${venues}">
        <g:each var="venue" in="${venues}">
          <li class="menuitem"><g:link controller="aboutUs" action="venue" id="${venue.id}" class="menuitem">${venue}</g:link>
              <g:if test="${venue.rooms}">
              <ul>
                <g:findAll var="room" in="${venue.rooms}" expr="it.publishState == 'Published'">
                <li class="menuitem"><g:link controller="aboutUs" action="room" id="${room.id}" class="menuitem">${room}</g:link></li>
                </g:findAll>
              </ul>
              </g:if>
          </li>
        </g:each>          
      </g:if>
      </ul>
  </li>              
</ul>