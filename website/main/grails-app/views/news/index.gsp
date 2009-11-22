<!DOCTYPE html>
<html>
<head>
    <title>Welcome Kagyu Samye Dzong London</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="layout" content="news">
</head>
<body>
    <div class="articles left">
        <ol>
            <g:each in="${articles}" status="i" var="articleInstance">
                <li class="article">
                    <h2><g:link action="view" id="${articleInstance.id}">${articleInstance.title}</g:link></h2>
                    <h3>by <a>Leanne Northrop</a></h3>
                    <h4>20 October, 2009</h4>
                    <p>${articleInstance.summary.encodeAsTextile()}</p>
                </li>
            </g:each>
        </ol>

        <g:if test="${articleInstanceTotal}">
            <div class="paginateButtons">
                <g:paginate total="${articleInstanceTotal}" />
            </div>
        </g:if>
    </div>
    <div class="right">
        <div>
            Notifications
            <ul>
                <li>Email Newsletter</li>
                <li>Twitter</li>
                <li>RSS</li>
            </ul>
        </div>
        <div class="articles">
            <h2>Archive</h2>
            <ol>
                <g:each in="${archivedArticles}" status="i" var="articleInstance">
                    <li class="article">
                        <h2><g:link action="view" id="${articleInstance.id}">${articleInstance.title}</g:link></h2>
                    </li>
                </g:each>
            </ol>
        </div>
    </div>
</body>
</html>