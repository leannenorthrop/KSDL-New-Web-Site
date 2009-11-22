<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Publish ${fieldValue(bean:articleInstance,field:'title')}</title>
    </head>
    <body>
        <div class="menuBar">
            <span class="menuButton"><g:link class="home" controller="manageSite" action="index">Home</g:link></span>
            <span class="menuButton"><g:link class="list" controller="article" action="manage" params="[offset:0,max:10]">Articles</g:link></span>
            <shiro:hasAnyRole in="['Author']"><span class="menuButton"><g:link class="create" controller="article" action="create">New Article</g:link></span></shiro:hasAnyRole>
            <shiro:hasAnyRole in="['Author']"><span class="menuButton"><g:link class="delete" controller="article" action="delete" onclick="return confirm('Are you sure?');" >Delete</g:link></span></shiro:hasAnyRole>
        </div>
        <div class="content">
             <g:form name="publish" action="publish" method="post" >
                <h1>Prepare Article for Publishing</h1>
                <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${articleInstance}">
                <div class="errors">
                    <g:renderErrors bean="${articleInstance}" as="list" />
                </div>
                </g:hasErrors>
                <input type="hidden" name="id" value="${articleInstance?.id}" />
                <input type="hidden" name="version" value="${articleInstance?.version}" />
                <input type="hidden" name="publishState" value="${articleInstance?.publishState}" />
                <input type="hidden" name="deleted" value="${articleInstance?.deleted}" />
                <shiro:lacksRole in="['Author']"><h2>${fieldValue(bean:articleInstance,field:'title')}</h2></shiro:lacksRole>
                <shiro:hasAnyRole in="['Author']">
                    <fieldset>
                        <label for="title">Title</label>
                        <input type="text" id="title" name="title" class="${hasErrors(bean:articleInstance,field:'title','errors')}" value="${fieldValue(bean:articleInstance,field:'title')}"/>
                    </fieldset>
                    <fieldset>
                        <label for="summary">Summary:</label>
                        <textarea rows="5" cols="40" name="summary" class="${hasErrors(bean:articleInstance,field:'summary','errors')}">${fieldValue(bean:articleInstance, field:'summary')}</textarea>
                    </fieldset>
                    <fieldset>
                        <label for="content">Content:</label>
                        <textarea rows="35" cols="40" name="content" class="${hasErrors(bean:articleInstance,field:'content','errors')}">${fieldValue(bean:articleInstance, field:'content')}</textarea>
                    </fieldset>
                </shiro:hasAnyRole>
                <fieldset>
                    <label for="tags">Tags</label>
                    <h4>Suggestions</h4>
                    <ul>
                        <li>News (Show on News page)</li>
                        <li>Meditation (Show on Mediation page)</li>
                        <li>"Meditation Advice" (Show on Meditation page under Advice)</li>
                    </ul>
                    <textarea cols="5" rows="2" id="tags" name="tags" class="${hasErrors(bean:articleInstance,field:'tags','errors')}" value="${articleInstance.tags.join(",")}"></textarea>
                </fieldset>
                <a class="submit" onClick="document.publish.submit();">Publish &raquo;</a>
            </g:form>
        </div>
    </body>
</html>
