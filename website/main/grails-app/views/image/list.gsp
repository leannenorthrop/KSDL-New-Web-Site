
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
            <ol>
                <g:each in="${imageInstanceList}" status="i" var="imageInstance">
                    <li>
                        <g:link action="show" id="${imageInstance.id}">
                            <img src="${createLink(controller:'image', action:'thumbnail', id:imageInstance.id)}"/>
                            <span>${fieldValue(bean:imageInstance, field:'name')}</span>
                        </g:link>
                    </li>
                </g:each>
            </ol>
            <div class="paginateButtons">
                <g:paginate total="${imageInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
