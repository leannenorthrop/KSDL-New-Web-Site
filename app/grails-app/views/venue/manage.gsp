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

<%@ page import="org.samye.dzong.london.venue.Venue" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="content-admin" />
    <title><g:message code="venue.manage.title"/></title>
  </head>
  <body>
  <g:set var="nameLabel"><g:message code="venue.name.label"/></g:set>
  <g:set var="lastUpdatedLabel"><g:message code="article.last.updated"/></g:set>
  <g:set var="deleteConfirmLabel"><g:message code="article.delete.confirm"/></g:set>
  <table>
    <thead>
      <tr>
    <g:sortableColumn property="name" title="${nameLabel}"/>
    <g:sortableColumn property="lastUpdated" title="${lastUpdatedLabel}"/>
    <th><g:message code="article.action.label"/></th>
</tr>
</thead>
<tbody>
<g:each in="${venues}" status="i" var="venue">
  <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
    <td>
  <g:link action="edit" id="${venue.id}">${fieldValue(bean: venue, field: 'name')}</g:link>
  </td>
  <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${venue?.lastUpdated}"/></td>
  <td>
  <g:link action="delete" id="${venue.id}" onclick="${deleteConfirmLabel}"><g:message code="article.delete.action"/></g:link>
  </td>
  </tr>
</g:each>
</tbody>
</table>
<g:if test="${total}">
  <div class="manage paginateButtons">
    <g:paginate total="${total}"/>
  </div>
</g:if> 
</body>
</html>
