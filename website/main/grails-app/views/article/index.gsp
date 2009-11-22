<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Kagyu Samye Dzong London: Article List</title>
    </head>
    <body>
        <div class="articles">
            <lsdc:nav />

            <ol>
                <g:each in="${articleInstanceList}" status="i" var="articleInstance">
                    <li class="article">
                        <h2><g:link action="view" id="${articleInstance.id}">${articleInstance.title}</g:link></h2>
                        <h3>by <a>Leanne Northrop</a></h3>
                        <h4>20 October, 2009</h4>
                        <p>${articleInstance.summary.encodeAsTextile()}</p>
                    </li>
                </g:each>
            </ol>

            <div class="paginateButtons">
                <g:paginate total="${articleInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
