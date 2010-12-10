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
  Template for displaying shorten list of news items.
  User: Leanne Northrop
  Date: Feb 12, 2010,8:49:38 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="box shortnews">
  <h3><g:message code="${heading}"/></h3>
  <ul>
    <g:each in="${articles}" status="i" var="articleInstance">
      <g:if test="${articleInstance}">
          <li class=" ${(i == 0 ? 'first' : '')?:(i == articles?.size() ? 'last' : '')}">
            <g:link controller="news" action="view" id="${articleInstance.id}">
              <h4>${articleInstance.title}</h4>                    
            </g:link>
          </li>
      </g:if>
    </g:each>
  </ul>
</div>
