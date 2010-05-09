
<%@ page import="org.samye.dzong.london.venue.Room" %>
<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Manage Room</title>
    </head>
    <body>
        <div class="content">
            <h1>Room List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="name" title="Name" />
                        
                   	        <g:sortableColumn property="description" title="Description" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${roomInstanceList}" status="i" var="roomInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${roomInstance.id}">${fieldValue(bean:roomInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:roomInstance, field:'name')}</td>
                        
                            <td>${fieldValue(bean:roomInstance, field:'description')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${roomInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
