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
<g:set var="titleLabel"><g:message code="room.name.label"/></g:set>
<g:set var="lastUpdatedLabel"><g:message code="room.last.updated"/></g:set>
<g:set var="categoryLabel"><g:message code="room.venue.label"/></g:set>
<g:set var="deleteConfirmLabel"><g:message code="room.delete.confirm"/></g:set>
<g:set var="roomHireLabel"><g:message code="room.forHire.label"/></g:set>
<g:set var="authorLabel"><g:message code="room.author.label"/></g:set>
<body>
    <table>
        <thead>
            <tr>
                <g:sortableColumn property="name" title="${titleLabel}" />
                <g:sortableColumn property="venue.id" title="${categoryLabel}"/>
                <g:sortableColumn property="forHire" title="${roomHireLabel}"/>                
                <g:sortableColumn property="lastUpdated" title="${lastUpdatedLabel}" />
                <shiro:hasAnyRole in="['Editor','Administrator']">
                <g:sortableColumn property="author" title="${authorLabel}"/>
                </shiro:hasAnyRole>
                <th><g:message code="room.action.label"/></th>
            </tr>
        </thead>
        <tbody>
        <g:each in="${rooms}" status="i" var="room">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                <td>
                    <shiro:hasAnyRole in="['Author','Administrator']">    
                    <g:link action="edit" id="${room.id}">${fieldValue(bean: room, field: 'title')}</g:link>
                    </shiro:hasAnyRole>
                    <shiro:lacksAllRoles in="['Author','Administrator']">
                    ${fieldValue(bean: room, field: 'title')}
                    </shiro:lacksAllRoles>                    
                </td>
                <td>${room?.venue?.name}</td>
                <td><g:message code="${room?.forHire ? 'true' : 'false'}"/></td>                
                <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${room?.lastUpdated}"/></td>
                <shiro:hasAnyRole in="['Editor','Administrator']">
                <td>${fieldValue(bean:room, field:'author')}</td>
                </shiro:hasAnyRole>
                <td>
                <shiro:hasAnyRole in="['Editor','Administrator']">
                <g:link action="changeState" params="[state:'Published']" id="${room.id}"><g:message code="room.publish.action"/></g:link>  
                </shiro:hasAnyRole>
                <shiro:hasAnyRole in="['Author']">
                <g:link action="delete" id="${room.id}" onclick="${deleteConfirmLabel}"><g:message code="room.delete.action"/></g:link>
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
