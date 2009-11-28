<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Kagyu Samye Dzong London: ${title}</title>
    </head>
    <body>
        <div class="articles">
            <ol>
                <g:each in="${articleInstanceList}" status="i" var="articleInstance">
                    <li class="article">
                        <h2><g:link action="view" id="${articleInstance.id}">${articleInstance.title}</g:link></h2>
                        <h3>by <a>${articleInstance.author.username}</a></h3>
                        <g:if test="${auditLogs[i][0] != null}">
                            <h4>Published <span class="pretty-date">${auditLogs[i][0].dateCreated.prettyDate()}</span></h4>
                        </g:if>
                        <p>${articleInstance.summary.encodeAsTextile()}</p>
                    </li>
                </g:each>
        </div>
    </body>
</html>
