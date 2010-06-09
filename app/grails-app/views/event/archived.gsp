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
  <g:set var="titleLabel"><g:message code="event.title.label"/></g:set>
  <g:set var="lastUpdatedLabel"><g:message code="event.last.updated"/></g:set>
  <g:set var="publishedOnLabel"><g:message code="event.eventDate.label"/></g:set>
  <g:set var="authorLabel"><g:message code="event.author.label"/></g:set>
  <g:set var="categoryLabel"><g:message code="event.category.label"/></g:set>
  <body>
    <table>
      <thead>
        <tr>
          <g:sortableColumn property="title" title="${titleLabel}"/>
          <g:sortableColumn property="category" title="${categoryLabel}"/>
          <th><g:message code="event.eventDate.label"/></th>
          <g:sortableColumn property="lastUpdated" title="${lastUpdatedLabel}"/>
          <shiro:hasAnyRole in="['Editor','Administrator']">
            <g:sortableColumn property="author" title="${authorLabel}"/>
          </shiro:hasAnyRole>
          <th><g:message code="event.action.label"/></th>
        </tr>
      </thead>
      <tbody>
        <g:each in="${events}" status="i" var="eventInstance">
          <g:set var="rule" value="${eventInstance?.dates.toArray()[0]}"/>
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td>
              <g:link action="show" id="${eventInstance.id}">${fieldValue(bean: eventInstance, field: 'title')}</g:link>
            </td>
            <td>
              ${fieldValue(bean: eventInstance, field: 'category')}
            </td>
            <td><g:formatDate format="dd-MM-yyyy" date="${rule?.startDate}"/></td>
            <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${eventInstance?.lastUpdated}"/></td>
            <shiro:hasAnyRole in="['Editor','Administrator']">
              <td>${fieldValue(bean: eventInstance, field: 'author')}</td>
            </shiro:hasAnyRole>
            <td>
              <shiro:hasAnyRole in="['Editor','Administrator']">
                <g:link action="changeState" params="[state:'Unpublished']" id="${eventInstance.id}"><g:message code="event.unpublish.action"/></g:link>
                <g:link action="delete" id="${eventInstance.id}" onclick="${deleteConfirmLabel}"><g:message code="event.delete.action"/></g:link>
              </shiro:hasAnyRole>
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
