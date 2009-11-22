<html>
<head>
    <title>Kagyu Samye Dzong London: News</title>
    <meta name="layout" content="news">
</head>
<body>
    <div class="main">
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

        <g:if test="${articlesTotal}">
            <div class="paginateButtons">
                <g:paginate total="${articlesTotal}" />
            </div>
        </g:if>
    </div>
    <div class="secondary">
        <div id="news-notifications">
            Notifications
            <ul>
                <li>Email Newsletter</li>
                <li>Twitter</li>
                <li>RSS</li>
            </ul>
        </div>
        <div id="news-archive">
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