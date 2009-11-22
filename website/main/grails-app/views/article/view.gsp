<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <title>Kagyu Samye Dzong London Articles: ${articleInstance.title}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
    </head>
    <body>
        <div class="article">
            <div class="wrap group">
                <div class="left">
                    <h3>${articleInstance.title}</h3>

                    <ul class="article-information">
                        <li>by <a>Leanne Northrop</a></li>
                        <li>20 October, 2009</li>
                    </ul>

                    <div class="body">
                        ${articleInstance.content.encodeAsTextile()}
                    </div><!-- /body -->
                </div><!-- /left -->

                <div class="right">
                    <h3>Other Articles</h3>
                    <ul>
                        <li>a</li>
                        <li>b</li>
                    </ul>
                </div>
            </div><!-- /group -->
        </div><!-- /article -->
    </body>
</html>