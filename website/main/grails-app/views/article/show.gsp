<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <title>Kagyu Samye Dzong London Articles: ${articleInstance.title}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
    </head>
    <body>
        <div class="menuBar">
            <span class="menuButton"><g:link class="home" controller="manageSite" action="index">Home</g:link></span>
            <shiro:hasAnyRole in="['Author']"><span class="menuButton"><g:link class="list" controller="article" action="manage">Articles</g:link></span></shiro:hasAnyRole>
        </div>
        <div class="content">
            <div class="article">
                <h1>${articleInstance.title}</h1>
                <p class="summary">${articleInstance.summary.encodeAsTextile()}</p>
                <div class="body">
                    ${articleInstance.content.encodeAsTextile()}
                </div>
                <shiro:hasAnyRole in="['Editor','Administrator']"><g:link action="button edit" id="${articleInstance.id}">Prepare for Publication</g:link></shiro:hasAnyRole>
            </div>
        </div>
    </body>
</html>