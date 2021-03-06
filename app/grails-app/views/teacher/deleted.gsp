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
<g:set var="titleLabel"><g:message code="teacher.name.label"/></g:set>
<g:set var="authorLabel"><g:message code="teacher.author.label"/></g:set>
<html>
  <body>
    <table>
      <thead>
        <tr>
      <g:sortableColumn property="title" title="${titleLabel}"/>
      <shiro:hasAnyRole in="['Editor','Admin','EventOrganiser']">
        <g:sortableColumn property="author" title="${authorLabel}"/>
      </shiro:hasAnyRole>
      <th><g:message code="teacher.action.label"/></th>
  </tr>
</thead>
<tbody>
<g:each in="${teachers}" status="i" var="teacher">
  <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
    <td>
  <g:link action="show" id="${teacher.id}">${teacher}</g:link>
  </td>
  <shiro:hasAnyRole in="['Editor','Admin','EventOrganiser']">
    <td>${fieldValue(bean: teacher, field: 'author')}</td>
  </shiro:hasAnyRole>
  <td>
  <shiro:hasAnyRole in="['Editor','Admin','EventOrganiser']">
    <g:link action="changeState" params="[state:'Unpublished']" id="${teacher.id}"><g:message code="teacher.unpublish.action"/></g:link>
  </shiro:hasAnyRole>
  </td>
  </tr>
</g:each>
</tbody>
</table>
<div class="paginateButtons">
  <g:paginate total="${total}"/>
</div>
</body>
</html>
