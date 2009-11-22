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
                <li><h3>20 October, 2009</h3></li>
                <li><h4>by <a>To Be Done</a></h4></li>
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