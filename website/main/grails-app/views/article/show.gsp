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
        <div class="col1_80_Percent article">
            <h2>${articleInstance.title}</h2>

            <ul>
                <li><h3>20 October, 2009</h3></li>
                <li><h4>by <a>To Be Done</a></h4></li>
            </ul>

            <div class="body">
                ${articleInstance.content.encodeAsTextile()}
            </div><!-- /body -->
        </div><!-- /left -->

        <div class="col2_20_Percent">
        </div>
    </body>
</html>