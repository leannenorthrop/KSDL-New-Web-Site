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
<g:set var="titleLabel"><g:message code="room.name.label"/></g:set>
<g:set var="authorLabel"><g:message code="room.author.label"/></g:set>
<html>
  <body>
    <table>
      <thead>
        <tr>
      <g:sortableColumn property="room" title="${titleLabel}"/>
      <shiro:hasAnyRole in="['Editor','Admin']">
        <g:sortableColumn property="author" title="${authorLabel}"/>
      </shiro:hasAnyRole>
      <th><g:message code="room.action.label"/></th>
  </tr>
</thead>
<tbody>
<g:each in="${rooms}" status="i" var="room">
  <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
    <td>
  <g:link action="show" id="${room.id}">${room}</g:link>
  </td>
  <shiro:hasAnyRole in="['Editor','Administrator']">
    <td>${fieldValue(bean: room, field: 'author')}</td>
  </shiro:hasAnyRole>
  <td>
  <shiro:hasAnyRole in="['Editor','Administrator','VenueManager']">
    <g:link action="changeState" params="[state:'Unpublished']" id="${room.id}"><g:message code="room.unpublish.action"/></g:link>
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
