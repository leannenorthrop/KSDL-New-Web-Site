<html>
<head>
    <title>Kagyu Samye Dzong London: Meditation</title>
    <meta name="layout" content="main">
</head>
<body>
    <div class="col1_80_Percent box">
        <h2>Experience</h2>
    </div>
    <div class="col2_20_Percent box">
        <h2>Video</h2>
    </div>
    <div class="col1_33_Percent">
        <div class="box">
            <h2>Resources</h2>
            <ol>
                <li><g:link controller="article" action="index" params="[tags:'Meditation,Meditation Advice,Meditation Benefits']">Articles</g:link></li>
            </ol>
        </div>
        <div class="articles box">
            <h2>Advice</h2>
            <ol>
                <g:each in="${adviceArticles}" status="i" var="articleInstance">
                    <li>
                        <h3>${articleInstance.title}</h3>
                        <g:if test="${articleInstance.image}">
                            <img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
                        </g:if>
                        <p>${articleInstance.summary.encodeAsTextile()} <g:link controller="article" action="view" id="${articleInstance.id}">Read More...</g:link></p>
                    </li>
                </g:each>
            </ol>
        </div>
        <div class="articles box">
            <h2>Benefits</h2>
            <ol>
                <g:each in="${benefitsArticles}" status="i" var="articleInstance">
                    <li>
                        <h3>${articleInstance.title}</h3>
                        <g:if test="${articleInstance.image}">
                            <img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
                        </g:if>
                        <p>${articleInstance.summary.encodeAsTextile()} <g:link controller="article" action="view" id="${articleInstance.id}">Read More...</g:link></p>
                    </li>
                </g:each>
            </ol>
        </div>
    </div>
    <div class="col2_66_Percent box">
        <h2>Courses</h2>
    </div>
</body>
</html>