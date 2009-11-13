<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>Welcome to Grails Kagyu Samye Dzong London</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Welcome</a></span>
            <span class="menuButton"><a class="news" href="${resource(dir:'')}">News</a></span>
            <span class="menuButton"><a class="events" href="${resource(dir:'')}">Events</a></span>
            <span class="menuButton"><a class="wellbeing" href="${resource(dir:'')}">Wellbeing</a></span>
            <span class="menuButton"><a class="community" href="${resource(dir:'')}">Community</a></span>
            <span class="menuButton"><a class="help" href="${resource(dir:'')}">Help</a></span>
            <span class="menuButton"><g:link controller="auth" action="index">Sign In</g:link></span>
            <span class="menuButton"><g:link controller="auth" action="signOut">Sign Out</g:link></span>
            <span class="menuButton"><a class="login" href="${resource(dir:'')}">Help</a></span>
        </div>
        <p>
            Playing around with Grails framework to see how much can be done. Will look very basic as this is concentrating on functionality and facilities to support content creation over design. Features to look at real soon:
            <ol>
                <li>textile markup</li>
                <li>Security with/without openid</li>
                <li>Dashboards</li>
                <li>and plenty more...</li>
            </ol>
        </p>
    </body>
</html>