<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Article List</title>
    </head>
    <body>
        <h1>Article List</h1>
        <div class="list">
            <ol>
                <g:each in="${articleInstanceList}" status="i" var="articleInstance">
                    <li>
                        <g:link action="view" id="${articleInstance.id}">${articleInstance.title}</g:link><br/ >
                        ${articleInstance.summary}
                    </li>
                </g:each>
            </ol>
        </div>
        <div class="paginateButtons">
            <g:paginate total="${articleInstanceTotal}" />
        </div>
    </body>
</html>
