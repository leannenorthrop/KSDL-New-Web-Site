<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <title>Kagyu Samye Dzong London Articles: ${articleInstance.title}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
    </head>
    <body>
        <div class="menuBar">
            <span class="menuButton"><g:link class="menuButton home" controller="manageSite" action="index">Home</g:link></span>
            <shiro:hasAnyRole in="['Author', 'Editor','Administrator']"><span class="menuButton"><g:link class="list" controller="article" action="manage" params="[offset:0,max:10]">Articles</g:link></span></shiro:hasAnyRole>
            <shiro:hasAnyRole in="['Editor','Administrator']"><g:if test="${articleInstance.publishState == 'Unpublished'}"><span class="menuButton"><g:link action="pre_publish" id="${articleInstance.id}">Publish</g:link></span></g:if></shiro:hasAnyRole>
            <shiro:hasAnyRole in="['Editor','Administrator']"><g:if test="${articleInstance.publishState == 'Published'}"><span class="menuButton"><g:link action="pre_publish" id="${articleInstance.id}">Edit</g:link></span></g:if></shiro:hasAnyRole>
        </div>
        <div class="content">
            <div class="article">
                <h1>${articleInstance.title}</h1>
                <p class="summary">${articleInstance.summary.encodeAsTextile()}</p>
                <div class="body">
                    ${articleInstance.content.encodeAsTextile()}
                </div>
            </div>
        </div>
    </body>
</html>