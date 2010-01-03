<html>
<head>
    <title>${title}</title>
    <meta name="layout" content="main">
</head>
<body>
    <h2>Meditation Articles</h2>
    <ol>
        <g:each in="${articles}" status="i" var="articleInstance">
            <li class="article">
                <h3><g:link action="view" id="${articleInstance.id}">${articleInstance.title}</g:link></h3>
                <h4>by <a>${articleInstance.author.username}</a></h4>
                <g:if test="${articleInstance.displayDate}">
                    <h5><g:formatDate format="dd MMMM, yyyy" date="${articleInstance.displayDate}"/></h5>
                </g:if>
                <g:if test="${articleInstance.image}">
                    <img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
                </g:if>
                <p>${articleInstance.summary}</p>
            </li>
        </g:each>
    </ol>
</body>
</html>
