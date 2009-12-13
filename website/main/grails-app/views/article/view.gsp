<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <title>Kagyu Samye Dzong London Articles: ${articleInstance.title}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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
                    <g:if test="${articleInstance.displayDate && auditLogs != null && auditLogs[0] != null && auditLogs[0].dateCreated != null}">
                        <li><h3><span class="date"><g:formatDate format="dd MMMM, yyyy" date="${auditLogs[0].dateCreated}"/></span></h3></li>
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
            <h2>Other Articles</h2>
            <ul>
                <li>Previous (To Do)</li>
                <li>Next (To Do)</li>
            </ul>
        </div>
    </body>
</html>