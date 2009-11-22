<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <title>Kagyu Samye Dzong London Articles: ${articleInstance.title}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
    </head>
    <body>
        <div class="content-admin">
        <h1>${articleInstance.title}</h1>
        <p>${articleInstance.summary}</p>
        <div>
            ${articleInstance.content}
        </div>
        <shiro:hasAnyRole in="['Editor','Administrator']"><span class="button"><g:link action="edit" id="${articleInstance.id}">Prepare for Publication</g:link></span></shiro:hasAnyRole>
        <span class="button"><g:link class="list" action="manage">Cancel</g:link></span>
        </div>
    </body>
</html>