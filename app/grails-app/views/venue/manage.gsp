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
        <g:set var="deleteConfirmLabel"><g:message code="article.delete.confirm"/></g:set>
        <table>
            <thead>
                <tr>
                    <g:sortableColumn property="name" titleKey="venue.name.label"/>
                    <g:sortableColumn property="lastUpdated" titleKey="article.last.updated"/>
                    <th style="min-width:4em;"><g:message code="article.action.label"/></th>
                </tr>
            </thead>
            <tbody>
                <g:each in="${venues}" status="i" var="venue">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td>${fieldValue(bean: venue, field: 'name')}</td>
                    <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${venue?.lastUpdated}"/></td>
                    <td class="actions">
                        <g:link action="edit" id="${venue.id}" class="edit" title="${message(code:'article.edit.action')}"><span class="silk-icon silk-icon-pencil">&nbsp;</span></g:link>
                        <g:remoteLink action="delete" id="${venue.id}" class="delete" asynchronous="false" update="jsmsgbox" title="${message(code:'article.delete.action')}" method="GET" after="window.location.reload();"><span class="silk-icon silk-icon-cancel">&nbsp;</span></g:remoteLink>
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
