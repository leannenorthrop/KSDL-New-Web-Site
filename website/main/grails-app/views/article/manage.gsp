
<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Article List</title>
    </head>
    <body>
        <div class="body">
            <h1>Articles</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <shiro:hasAnyRole in="['Author']"><span class="button"><g:link class="create" action="create">Create New Article</g:link></span></shiro:hasAnyRole>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="title" title="Title" />

                            <shiro:hasAnyRole in="['Editor','Administrator']"><g:sortableColumn property="publishState" title="Person" /></shiro:hasAnyRole>

                            <g:sortableColumn property="publishState" title="Publish State" />

                            <shiro:hasAnyRole in="['Editor','Administrator']"><th>Change State</th></shiro:hasAnyRole>

                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${articleInstanceList}" status="i" var="articleInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                            <td>
                                <shiro:hasAnyRole in="['Editor','Administrator']"><g:link action="show" id="${articleInstance.id}">${fieldValue(bean:articleInstance, field:'title')}</g:link></shiro:hasAnyRole>
                                <shiro:hasAnyRole in="['Author']"><g:link action="edit" id="${articleInstance.id}">${fieldValue(bean:articleInstance, field:'title')}</g:link></shiro:hasAnyRole>
                            </td>

                            <shiro:hasAnyRole in="['Editor','Administrator']"><td>${fieldValue(bean:articleInstance, field:'author')}</td></shiro:hasAnyRole>

                            <td>${fieldValue(bean:articleInstance, field:'publishState')}</td>

                            <shiro:hasAnyRole in="['Editor','Administrator']"><th>if unpublished then publish, if publish then archive or unpublish</th></shiro:hasAnyRole>

                            <td><g:link action="delete" id="${articleInstance.id}" onclick="return confirm('Are you sure?');" >Delete</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${articleInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
