<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Edit Article</title>
    </head>
    <body>
        <div class="content">
             <g:form id="updatearticle" name="updatearticle" method="post" >
                <h1>Update Article</h1>
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
                <a class="submit" onClick="document.updatearticle.submit();">Save Changes &raquo;</a>
            </g:form>
        </div>
    </body>
</html>
