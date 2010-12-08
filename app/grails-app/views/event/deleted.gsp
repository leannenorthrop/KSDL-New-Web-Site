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
    <g:if test="${params.max}">
        <g:set var="listMaxParam" value="${params.max}"/>
    </g:if>
    <g:else>
        <g:set var="listMaxParam" value="50"/>
    </g:else>
    <g:set var="deleteConfirmLabel"><g:message code="article.delete.confirm"/></g:set>
    
    <body>
        <table>
            <thead>
            <tr>
                <g:sortableColumn property="title" titleKey="event.title.label" params="${[max:listMaxParam]}" style="width:20em"/>
                <g:sortableColumn property="dateCreated" titleKey="event.created.on" params="${[max:listMaxParam]}"/>                  
                <g:sortableColumn property="lastUpdated" titleKey="event.last.updated" params="${[max:listMaxParam]}"/>
                <shiro:hasAnyRole in="${flash.adminRoles}">
                <g:sortableColumn property="author" titleKey="event.author.label" params="${[max:listMaxParam]}"/>
                </shiro:hasAnyRole>
                <th style="min-width:2em;"><g:message code="article.action.label"/></th>
            </tr>
            </thead>
            <tbody>
                <g:each in="${events}" status="i" var="event">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td>
                        ${fieldValue(bean: event, field: 'title')}
                    </td>
                    <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${event?.dateCreated}"/></td>                       
                    <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${event?.lastUpdated}"/></td>
                    <shiro:hasAnyRole in="${flash.adminRoles}">
                    <td>${fieldValue(bean: event, field: 'author')}</td>
                    </shiro:hasAnyRole>
                    <td class="actions">
                        <g:remoteLink action="changeState" params="[state:'Unpublished']" id="${event.id}" title="${message(code:'article.unpublish.action')}" asynchronous="false" update="jsmsgbox" method="GET" after="updateTabs(3);"><span class="silk-icon silk-icon-arrow-undo">&nbsp;</span></g:remoteLink>                        
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
