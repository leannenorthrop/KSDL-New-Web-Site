<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Manage Site Content</title>
    </head>
    <body>
        <div class="menuBar">
            <span class="menuButton"><g:link class="home" controller="manageSite" action="index">Home</g:link></span>
            <shiro:hasAnyRole in="['Editor','Author','Administrator']"><span class="menuButton"><g:link class="list" controller="article" action="manage" params="[offset:0,max:10]">Articles</g:link></span></shiro:hasAnyRole>
            <shiro:hasAnyRole in="['Author','Administrator']"><span class="menuButton"><g:link class="list" controller="image" action="manage" params="[offset:0,max:10]">Images</g:link></span></shiro:hasAnyRole>
        </div>
        <div class="content">
        </div>
    </body>
</html>
