<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <title>Kagyu Samye Dzong London Articles: ${articleInstance.title}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
    </head>
    <body>
        <div class="col1_80_Percent article">
            <h2>${articleInstance.title}</h2>

            <ul>
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
        </div>
    </body>
</html>