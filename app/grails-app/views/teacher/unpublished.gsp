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
<html>
<g:set var="titleLabel"><g:message code="teacher.name.label"/></g:set>
<g:set var="lastUpdatedLabel"><g:message code="teacher.last.updated"/></g:set>
<g:set var="deleteConfirmLabel"><g:message code="teacher.delete.confirm"/></g:set>
<g:set var="authorLabel"><g:message code="teacher.author.label"/></g:set>
<body>
    <table>
        <thead>
            <tr>
                <g:sortableColumn property="title" title="${titleLabel}" />
                <g:sortableColumn property="lastUpdated" title="${lastUpdatedLabel}" />
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
                    <shiro:hasAnyRole in="['Author','Admin','EventOrganiser']">    
                    <g:link action="edit" id="${teacher.id}">${teacher}</g:link>
                    </shiro:hasAnyRole>
                    <shiro:lacksAllRoles in="['Author','Admin','EventOrganiser']">
                    ${teacher}
                    </shiro:lacksAllRoles>                    
                </td>
                <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${teacher?.lastUpdated}"/></td>
                <shiro:hasAnyRole in="['Editor','Admin','EventOrganiser']">
                <td>${fieldValue(bean:teacher, field:'author')}</td>
                </shiro:hasAnyRole>
                <td>
                <shiro:hasAnyRole in="['Editor','Admin','EventOrganiser']">
                <g:link action="changeState" params="[state:'Published']" id="${teacher.id}"><g:message code="teacher.publish.action"/></g:link>  
                </shiro:hasAnyRole>
                <shiro:hasAnyRole in="['Author','EventOrganiser','Admin']">
                <g:link action="delete" id="${teacher.id}" onclick="${deleteConfirmLabel}"><g:message code="teacher.delete.action"/></g:link>
                </shiro:hasAnyRole>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <div class="manage paginateButtons">
        <g:paginate total="${total}" />
    </div>
</body>
</html>

