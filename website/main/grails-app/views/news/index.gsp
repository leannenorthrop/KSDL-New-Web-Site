<html>
<head>
    <title>Kagyu Samye Dzong London: News</title>
    <meta name="layout" content="main">
</head>
<body>
        <div class="col1_66_Percent articles box">
        <h2>News <span class="amp">&amp;</span> Goings On</h2>
        <ol>
            <g:each in="${articles}" status="i" var="articleInstance">
                <li class="article">
                    <h3><g:link controller="article" action="view" id="${articleInstance.id}">${articleInstance.title}</g:link></h3>
                    <h4>by <a>${articleInstance.author.username}</a></h4>
                    <g:if test="${auditDetails != null && auditDetails[i][0] != null}">
                        <h5><g:formatDate format="dd MMMM, yyyy" date="${auditDetails[i][0].dateCreated}"/></h5>
                    </g:if>
                    <p>${articleInstance.summary}</p>
                </li>
            </g:each>
        </ol>
    </div>
    <div class="col2_33_Percent">
        <div id="news-notifications" class="box">
            <h2>Notifications</h2>
            <ul>
                <li>&#9993; Sign up for Email newsletter</li>
                <li>Follow us on Twitter</li>
                <li>RSS Feed</li>
            </ul>
        </div>
        <div id="news-archive" class="box">
            <h2>Older News Items</h2>
            <ul>
                <!-- TODO mark last child -->
                <g:each in="${archivedArticles}" status="i" var="articleInstance">
                    <li class="article">
                        <span><g:link controller="article" action="view" id="${articleInstance.id}">${articleInstance.title}</g:link></span>
                        <em>4th January, 2009</em>
                    </li>
                </g:each>
            </ul>
        </div>
    </div>
</body>
</html>