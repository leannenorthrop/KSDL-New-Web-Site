<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Kagyu Samye Dzong London: Manage Site Content</title>
    </head>
    <body>
        <div class="content-admin">
            <lsdc:nav />
        <h1>Manage Content</h1>

            Please select one of the following areas you would like to manage:
            <ol>
                <shiro:hasAnyRole in="['Editor','Author','Administrator']"><g:link controller="article" action="manage">Articles</g:link></shiro:hasAnyRole>
            </ol>
        </div>
    </body>
</html>
