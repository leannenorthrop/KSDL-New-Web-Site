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
                    <g:sortableColumn property="name" titleKey="room.name.label" params="${[max:listMaxParam]}" style="width:20em"/>
                    <g:sortableColumn property="forHire" titleKey="room.forHire.label" params="${[max:listMaxParam]}"/>                                
                    <g:sortableColumn property="venue" titleKey="room.venue.label" params="${[max:listMaxParam]}"/>                                                    
                    <g:sortableColumn property="dateCreated" titleKey="room.created.on" params="${[max:listMaxParam]}"/>                     
                    <g:sortableColumn property="lastUpdated" titleKey="room.last.updated" params="${[max:listMaxParam]}"/>                   
                    <shiro:hasAnyRole in="['Administrator','VenueManager']">
                    <g:sortableColumn property="author" titleKey="room.author.label" params="${[max:listMaxParam]}"/>
                    </shiro:hasAnyRole>
                    <th style="min-width:4em;"><g:message code="room.action.label"/></th>
                </tr>
            </thead>
            <tbody>
                <g:each in="${rooms}" status="i" var="room">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td>${fieldValue(bean: room, field: 'name')}</td>
                    <td><g:message code="${room?.forHire ? 'true' : 'false'}"/></td>                    
                    <td>${fieldValue(bean: room, field: 'venue')}</td>                        
                    <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${room?.dateCreated}"/></td>                      
                    <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${room?.lastUpdated}"/></td>                  
                    <shiro:hasAnyRole in="['Administrator','VenueManager']">
                        <td>${fieldValue(bean: room, field: 'author')}</td>
                    </shiro:hasAnyRole>
                    <td class="actions">
                        <g:link action="edit" id="${room.id}" class="edit" title="${message(code:'article.edit.action')}"><span class="silk-icon silk-icon-pencil">&nbsp;</span></g:link>
                        <g:remoteLink action="changeState" params="[state:'Ready']" class="ready" id="${room.id}" asynchronous="false" update="jsmsgbox" title="${message(code:'article.prepublish.action')}" method="GET" after="updateTabs(0);"><span class="silk-icon silk-icon-accept">&nbsp;</span></g:remoteLink>
                        <g:remoteLink action="delete" id="${room.id}" class="delete" asynchronous="false" update="jsmsgbox" title="${message(code:'article.delete.action')}" method="GET" after="updateTabs(0);"><span class="silk-icon silk-icon-cancel">&nbsp;</span></g:remoteLink>
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

