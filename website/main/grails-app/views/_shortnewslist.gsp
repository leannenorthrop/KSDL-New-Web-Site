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
  Created by IntelliJ IDEA.
  User: northrl
  Date: Feb 12, 2010
  Time: 8:49:38 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<h2><g:message code="${heading}"/></h2>
<ol>
  <g:each in="${articles}" status="i" var="articleInstance">
    <li>
      <g:link controller="news" action="view" id="${articleInstance.id}">
        <p>${articleInstance.title}</p>
        <g:if test="${articleInstance.displayDate}">
          <em><g:formatDate format="dd MMMM, yyyy" date="${articleInstance.datePublished}"/></em>
        </g:if>
        <p><g:message code="content.more"/></p>
      </g:link>
    </li>
  </g:each>
</ol>
