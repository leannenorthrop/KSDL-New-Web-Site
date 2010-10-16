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
  List of articles ready for publication
  User: Leanne Northrop
  Date: Feb 18, 2010,3:21:19 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <g:set var="titleLabel"><g:message code="article.title.label"/></g:set>
  <g:set var="lastUpdatedLabel"><g:message code="article.last.updated"/></g:set>
  <g:set var="deleteConfirmLabel"><g:message code="article.delete.confirm"/></g:set>
  <g:set var="authorLabel"><g:message code="article.author.label"/></g:set>
  <g:set var="eventDateLabel"><g:message code="event.eventDate.label"/></g:set>

  <body>
    <table>
      <thead>
        <tr>
          <g:sortableColumn property="title" title="${titleLabel}"/>
          <th><g:message code="event.eventDate.label"/></th>
          <g:sortableColumn property="lastUpdated" title="${lastUpdatedLabel}"/>
          <shiro:hasAnyRole in="['Editor','Admin']">
            <g:sortableColumn property="author" title="${authorLabel}"/>
          </shiro:hasAnyRole>
          <th><g:message code="article.action.label"/></th>
        </tr>
      </thead>
      <tbody>
        <g:each in="${events}" status="i" var="eventInstance">
          <g:set var="rule" value="${eventInstance?.dates.toArray()[0]}"/>
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td>
              <shiro:hasRole name="Author">
                <g:link action="edit" id="${eventInstance.id}">${fieldValue(bean: eventInstance, field: 'title')}</g:link>
              </shiro:hasRole>
              <shiro:lacksRole name="Author">
                ${fieldValue(bean: eventInstance, field: 'title')}
              </shiro:lacksRole>
            </td>
            <td><g:formatDate format="dd-MM-yyyy" date="${rule?.startDate}"/></td>
            <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${eventInstance?.lastUpdated}"/></td>
            <shiro:hasAnyRole in="['Editor','Admin']">
              <td>${fieldValue(bean: eventInstance, field: 'author')}</td>
            </shiro:hasAnyRole>
            <td>
              <shiro:hasAnyRole in="['Admin','EventOrganiser']">
                <g:link action="changeState" params="[state:'Ready For Publication']" id="${eventInstance.id}"><g:message code="event.ready.action"/></g:link>              
              </shiro:hasAnyRole>
              <shiro:hasAnyRole in="['Editor','Admin']">
                <g:link action="pre_publish" id="${eventInstance.id}"><g:message code="article.publish.action"/></g:link>
              </shiro:hasAnyRole>
              <g:link action="delete" id="${eventInstance.id}" onclick="${deleteConfirmLabel}"><g:message code="article.delete.action"/></g:link>
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
