
<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: View Image</title>
    </head>
    <body>
        <div class="content">
            <h2>${fieldValue(bean:imageInstance, field:'name')}</h2>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <img src="${createLink(controller:'image', action:'src', id:imageInstance.id)}"/>
            <ul>
                <g:each in="${imageInstance.tags}" status="i" var="tag">
                    <li>${tag}</li>
                </g:each>
            </ul>
        </div>
    </body>
</html>
