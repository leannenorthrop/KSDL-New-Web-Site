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

            <ul>
                <g:if test="${articleInstance.image}">
                    <li><img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/></li>
                </g:if>
            <g:if test="${auditLogs != null && auditLogs[0] != null && auditLogs[0].dateCreated != null}">
                <li><h3><span class="date"><g:formatDate format="dd MMMM, yyyy" date="${auditLogs[0].dateCreated}"/></span></h3></li>
            </g:if>
                <li><h4>by <a>${articleInstance.author.username}</a></h4></li>
            </ul>

            <div class="body">
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