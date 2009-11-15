<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Kagyu Samye Dzong London: Article List</title>
    </head>
    <body>
        <div class="articles">
            <lsdc:nav />

            <ol class="list">
                <g:each in="${articleInstanceList}" status="i" var="articleInstance">
                    <li><g:link action="view" id="${articleInstance.id}">${articleInstance.title}</g:link>${articleInstance.summary}</li>
                </g:each>
            </ol>

            <div class="paginateButtons">
            <g:paginate total="${articleInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
