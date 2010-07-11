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
<g:set var="titleLabel"><g:message code="product.title.label"/></g:set>
<g:set var="lastUpdatedLabel"><g:message code="product.last.updated"/></g:set>
<g:set var="deleteConfirmLabel"><g:message code="product.delete.confirm"/></g:set>
<body>
    <table>
        <thead>
            <tr>
                <g:sortableColumn property="title" title="${titleLabel}" />
                <g:sortableColumn property="lastUpdated" title="${lastUpdatedLabel}" />
                <th><g:message code="product.action.label"/></th>
            </tr>
        </thead>
        <tbody>
        <g:each in="${products}" status="i" var="product">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                <td>
                    <g:link action="edit" id="${product.id}">${product}</g:link>
                </td>
                <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${product?.lastUpdated}"/></td>
                <td>
                    <g:link action="changeState" params="[state:'Published']" id="${product.id}"><g:message code="product.publish.action"/></g:link>  
                    <g:link action="delete" id="${product.id}" onclick="${deleteConfirmLabel}"><g:message code="product.delete.action"/></g:link>
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
