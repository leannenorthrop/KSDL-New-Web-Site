
<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Manage Images</title>
    </head>
    <body>
        <div class="content">
            <h1>Image List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>

                            <g:sortableColumn property="id" title="Id" />

                            <g:sortableColumn property="name" title="Name" />

                            <g:sortableColumn property="image" title="Image" />

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${imageInstanceList}" status="i" var="imageInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                            <td><g:link action="show" id="${imageInstance.id}">${fieldValue(bean:imageInstance, field:'id')}</g:link></td>

                            <td>${fieldValue(bean:imageInstance, field:'name')}</td>

                            <td><img src="${createLink(controller:'image', action:'data', id:imageInstance.id)}"/></td>

                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${imageInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
