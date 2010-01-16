
<%@ page import="org.samye.dzong.london.events.Event" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'event.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="publishState" title="${message(code: 'event.publishState.label', default: 'Publish State')}" />
                        
                            <th><g:message code="event.author.label" default="Author" /></th>
                   	    
                            <g:sortableColumn property="displayAuthor" title="${message(code: 'event.displayAuthor.label', default: 'Display Author')}" />
                        
                            <g:sortableColumn property="displayDate" title="${message(code: 'event.displayDate.label', default: 'Display Date')}" />
                        
                            <g:sortableColumn property="title" title="${message(code: 'event.title.label', default: 'Title')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${eventInstanceList}" status="i" var="eventInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${eventInstance.id}">${fieldValue(bean: eventInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: eventInstance, field: "publishState")}</td>
                        
                            <td>${fieldValue(bean: eventInstance, field: "author")}</td>
                        
                            <td><g:formatBoolean boolean="${eventInstance.displayAuthor}" /></td>
                        
                            <td><g:formatBoolean boolean="${eventInstance.displayDate}" /></td>
                        
                            <td>${fieldValue(bean: eventInstance, field: "title")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${eventInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
