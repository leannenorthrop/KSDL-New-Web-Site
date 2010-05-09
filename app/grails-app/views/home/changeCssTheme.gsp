<html>
<head>
    <title>Change Theme</title>
    <meta name="layout" content="main">
</head>
<body>
    <div class="grid_16">
        <g:set var="cssThemesDir" value="${new File(application.getRealPath('/css/themes')).listFiles()}" />
        <h1>Change Theme</h1>
        <p><g:if test="${cookie(name:'cssTheme')}">You are currently viewing <g:cookie name="cssTheme" />.</g:if> You can change the way this website looks by clicking any of the links below:<br/><br/>
            <ul>
            <g:each in="${cssThemesDir}">
                <g:if test="${it.isDirectory()}">
                    <g:set var="cssThemeName" value="${it.name}"/>
                    <li><g:link action="setCSSTheme" id="${cssThemeName}">${cssThemeName}</g:link></li>
                </g:if>
            </g:each>
            </ul>
        </p>
    </div>
</body>
</html>
