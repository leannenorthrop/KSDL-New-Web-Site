<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Manage Site Content</title>
    </head>
    <body>
        <h1>Please select one of the following areas you would like to manage:</h1>
        <ol>
            <li><shiro:hasAnyRole in="['Editor','Author','Administrator']"><g:link controller="article" action="manage">Articles</g:link></shiro:hasAnyRole></li>
        </ol>
    </body>
</html>
