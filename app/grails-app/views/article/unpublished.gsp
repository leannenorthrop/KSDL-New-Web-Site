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
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <g:set var="titleLabel"><g:message code="article.title.label"/></g:set>
  <g:set var="lastUpdatedLabel"><g:message code="article.last.updated"/></g:set>
  <g:set var="deleteConfirmLabel"><g:message code="article.delete.confirm"/></g:set>
  <g:set var="authorLabel"><g:message code="article.author.label"/></g:set>
  <body>
    <table>
      <thead>
        <tr>
          <g:sortableColumn property="title" title="${titleLabel}"/>
          <g:sortableColumn property="lastUpdated" title="${lastUpdatedLabel}"/>
          <shiro:hasAnyRole in="['Admin']">
            <g:sortableColumn property="author" title="${authorLabel}"/>
          </shiro:hasAnyRole>
          <th><g:message code="article.action.label"/></th>
        </tr>
      </thead>
      <tbody>
        <g:each in="${articles}" status="i" var="articleInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td>
              <shiro:hasAnyRole in="['Author','Admin']">    
              <g:link action="edit" id="${articleInstance.id}">${fieldValue(bean: articleInstance, field: 'title')}</g:link>
              </shiro:hasAnyRole>
              <shiro:lacksAllRoles in="['Author','Admin']">
              ${fieldValue(bean: articleInstance, field: 'title')}
              </shiro:lacksAllRoles>              
            </td>
            <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${articleInstance?.lastUpdated}"/></td>
            <shiro:hasAnyRole in="['Admin']">
              <td>${fieldValue(bean: articleInstance, field: 'author')}</td>
            </shiro:hasAnyRole>
            <td>
                <g:link action="changeState" params="[state:'Ready For Publication']" id="${articleInstance.id}"><g:message code="article.prepublish.action"/></g:link>
                <g:link action="delete" id="${articleInstance.id}" onclick="${deleteConfirmLabel}"><g:message code="article.delete.action"/></g:link>
            </td>
          </tr>
        </g:each>
      </tbody>
    </table>
    <div class="manage paginateButtons">
      <g:paginate total="${total}"/>
    </div>
  </body>
</html>
