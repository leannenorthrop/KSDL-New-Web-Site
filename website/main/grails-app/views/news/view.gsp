<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <title>${articleInstance.title}</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <div class="col1_80_Percent article">
            <h2>${articleInstance.title}</h2>

            <g:if test="${articleInstance.displayAuthor || articleInstance.displayDate}">
            <ul>
                <g:if test="${articleInstance.image}">
                    <li><img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/></li>
                </g:if>
                <g:if test="${articleInstance.displayDate && articleInstance.publishedOn}">
                    <li><h3><span class="date"><g:formatDate format="dd MMMM, yyyy" date="${articleInstance.publishedOn}"/></span></h3></li>
                </g:if>
                <g:if test="${articleInstance.displayAuthor}">
                    <li><h4>by <a>${articleInstance.author.username}</a></h4></li>
                </g:if>
            </ul>
            </g:if>

            <g:if test="${!articleInstance.displayAuthor && !articleInstance.displayDate}">
                <div class="bodyNoDetails group">
                    <g:if test="${articleInstance.image}">
                    <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
                    </g:if>
            </g:if>
            <g:if test="${articleInstance.displayAuthor || articleInstance.displayDate}">
                <div class="body group">
            </g:if>
                ${articleInstance.content.encodeAsTextile()}
            </div><!-- /body -->
        </div><!-- /left -->

        <div class="col2_20_Percent">
            <h2>Other Similar Articles</h2>
            <ul>
                <g:each in="${articles}" status="i" var="articleInstance">
                <li class="article">
                    <h3><g:link action="view" id="${articleInstance.id}">${articleInstance.title}</g:link></h3>
                    <h4>by <a>${articleInstance.author.username}</a></h4>
                    <g:if test="${articleInstance.displayDate}">
                        <h5><g:formatDate format="dd MMMM, yyyy" date="${articleInstance.displayDate}"/></h5>
                    </g:if>
                    <g:if test="${articleInstance.image}">
                        <img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}" />
                    </g:if>
                </li>
                </g:each>
            </ul>
        </div>
    </body>
</html>
