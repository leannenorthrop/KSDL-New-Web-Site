<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <title>Kagyu Samye Dzong London Articles: ${articleInstance.title}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
    </head>
    <body>
        <h1>${articleInstance.title}</h1>
        <p>${articleInstance.summary}</p>
        <div>
            ${articleInstance.content}
        </div>
        <span class="button"><g:link action="edit" id="${articleInstance.id}">Edit</g:link></span>
    </body>
</html>