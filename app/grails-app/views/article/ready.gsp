%{------------------------------------------------------------------------------
     Copyright © 2010 Leanne Northrop

     This file is part of Samye Content Management System.

     Samye Content Management System is free software: you can redistribute it
     and/or modify it under the terms of the GNU General Public License as
     published by the Free Software Foundation, either version 3 of the License,
     or (at your option) any later version.

     Samye Content Management System is distributed in the hope that it will be
     useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with Samye Content Management System.
     If not, see <http://www.gnu.org/licenses/>.

     BT plc, hereby disclaims all copyright interest in the program
     “Samye Content Management System” written by Leanne Northrop.
  ----------------------------------------------------------------------------}%

<%--
    List of articles ready for publication
    User: Leanne Northrop
    Date: Feb 18, 2010,3:21:19 PM
--%>

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
                <g:sortableColumn property="title" title="${titleLabel}" titleKey="article.title.label" params="${[max:listMaxParam]}" style="width:20em"/>
                <g:sortableColumn property="dateCreated" titleKey="article.created.on" params="${[max:listMaxParam]}"/>                  
                <g:sortableColumn property="lastUpdated" title="${lastUpdatedLabel}" titleKey="article.last.updated" params="${[max:listMaxParam]}"/>
                <shiro:hasAnyRole in="['Editor','Administrator']">
                <g:sortableColumn property="author" title="${authorLabel}" titleKey="article.author.label" params="${[max:listMaxParam]}"/>
                </shiro:hasAnyRole>
                <th style="min-width:5em;"><g:message code="article.action.label"/></th>
            </tr>
            </thead>
            <tbody>
                <g:each in="${articles}" status="i" var="articleInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td>
                        ${fieldValue(bean: articleInstance, field: 'title')}
                    </td>
                    <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${articleInstance?.dateCreated}"/></td>                       
                    <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${articleInstance?.lastUpdated}"/></td>
                    <shiro:hasAnyRole in="['Editor','Administrator']">
                    <td>${fieldValue(bean: articleInstance, field: 'author')}</td>
                    </shiro:hasAnyRole>
                    <td class="actions">
                        <shiro:hasRole name="Author">
                        <g:link action="edit" id="${articleInstance.id}" class="edit" title="${message(code:'article.edit.action')}"><span class="silk-icon silk-icon-pencil">&nbsp;</span></g:link>
                        </shiro:hasRole>
                        <shiro:hasRole name="Editor">
                        <g:link action="show" id="${articleInstance.id}" class="show" title="${message(code:'article.show.action')}"><span class="silk-icon silk-icon-page-white-text">&nbsp;</span></g:link>
                        </shiro:hasRole>                        
                        <shiro:hasAnyRole in="['Author','Editor','Administrator']">
                        <g:remoteLink action="changeState" params="[state:'Unpublished']" id="${articleInstance.id}" title="${message(code:'article.unpublish.action')}" asynchronous="false" update="jsmsgbox" method="GET" after="updateTabs(1);"><span class="silk-icon silk-icon-arrow-undo">&nbsp;</span></g:remoteLink>
                        <g:remoteLink action="delete" id="${articleInstance.id}" class="delete" title="${message(code:'article.delete.action')}" asynchronous="false" update="jsmsgbox" method="GET" after="updateTabs(1);"><span class="silk-icon silk-icon-cancel">&nbsp;</span></g:remoteLink>
                        </shiro:hasAnyRole>
                        <shiro:hasAnyRole in="['Editor','Administrator']">
                        <g:link action="pre_publish" id="${articleInstance.id}" title="${message(code:'article.publish.action')}"><span class="silk-icon silk-icon-accept">&nbsp;</span></g:link>                        
                        </shiro:hasAnyRole>
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