<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Manage Articles</title>
    </head>
    <body>
        <div class="menuBar">
            <span class="menuButton"><g:link class="home" controller="manageSite" action="index">Home</g:link></span>
            <shiro:hasAnyRole in="['Author']"><span class="menuButton"><g:link class="create" controller="article" action="create">New Article</g:link></span></shiro:hasAnyRole>
        </div>
        <div class="content">
            <h1>Articles</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="title" title="Title" />

                            <shiro:hasAnyRole in="['Editor','Administrator']"><g:sortableColumn property="publishState" title="Person" /></shiro:hasAnyRole>

                            <g:sortableColumn property="publishState" title="Published" />

                            <shiro:hasAnyRole in="['Editor','Administrator']">
                                <th></th>
                            </shiro:hasAnyRole>

                            <shiro:hasAnyRole in="['Author']"><th></th></shiro:hasAnyRole>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${articleInstanceList}" status="i" var="articleInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                            <td>
                                <shiro:hasAnyRole in="['Author','Editor','Administrator']">
                                    <shiro:hasAnyRole in="['Author']">
                                        <g:if test="${articleInstance.publishState == 'Unpublished'}">
                                            <g:link action="edit" id="${articleInstance.id}">${fieldValue(bean:articleInstance, field:'title')}</g:link>
                                        </g:if>
                                    </shiro:hasAnyRole>
                                    <g:if test="${articleInstance.publishState == 'Published' || articleInstance.publishState == 'Archived' }">
                                        <g:link action="show" id="${articleInstance.id}">${fieldValue(bean:articleInstance, field:'title')}</g:link>
                                    </g:if>
                                </shiro:hasAnyRole>
                            </td>

                            <shiro:hasAnyRole in="['Editor','Administrator']"><td>${fieldValue(bean:articleInstance, field:'author')}</td></shiro:hasAnyRole>

                            <td>
                                <g:if test="${articleInstance.publishState == 'Unpublished'}">
                                    <span class="">No</span>
                                </g:if>
                                <g:if test="${articleInstance.publishState == 'Published'}">
                                    <span class="">Yes</span>
                                </g:if>
                            </td>

                            <shiro:hasAnyRole in="['Editor','Administrator']">
                                <td>
                                    <g:if test="${articleInstance.publishState == 'Unpublished'}">
                                        <g:link action="pre_publish" id="${articleInstance.id}">Publish</g:link>
                                    </g:if>
                                    <g:if test="${articleInstance.publishState == 'Published'}">
                                        <g:link action="pre_publish" id="${articleInstance.id}">Edit</g:link>
                                        <br />
                                        <g:link action="changeState" id="${articleInstance.id}" params="[state:'Unpublished']" onclick="return confirm('Are you sure?');" >Unpublish</g:link>
                                        <br /><g:link action="changeState" id="${articleInstance.id}" params="[state:'Archived']" onclick="return confirm('Are you sure?');" >Archive</g:link>
                                    </g:if>
                                    <br/>
                                    <g:link action="delete" id="${articleInstance.id}" onclick="return confirm('Are you sure?');" >Delete</g:link>
                                </td>
                            </shiro:hasAnyRole>

                            <shiro:hasAnyRole in="['Author']">
                                <td>
                                     <shiro:hasAnyRole in="['Author']"><g:if test="${articleInstance.publishState == 'Unpublished'}"><g:link action="delete" id="${articleInstance.id}" onclick="return confirm('Are you sure?');">Delete</g:link></g:if></shiro:hasAnyRole>
                                </td>
                            </shiro:hasAnyRole>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="manage paginateButtons">
                <g:paginate total="${articleTotal}" />
            </div>
        </div>
    </body>
</html>
