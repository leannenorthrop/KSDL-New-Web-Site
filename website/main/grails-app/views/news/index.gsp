<html>
<head>
    <title>Kagyu Samye Dzong London: News</title>
    <meta name="layout" content="main">
</head>
<body>
    <div class="content">
        <div class="main">
            <h2>News <span class="amp">&amp;</span> Goings On</h2>
            <ol>
                <g:each in="${articles}" status="i" var="articleInstance">
                    <li class="article">
                        <h3><g:link action="view" id="${articleInstance.id}">${articleInstance.title}</g:link></h3>
                        <h4>by <a>Leanne Northrop</a></h4>
                        <h5>20 October, 2009</h5>
                        <p>${articleInstance.summary.encodeAsTextile()}</p>
                    </li>
                </g:each>
            </ol>
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
    </div>
</body>
</html>