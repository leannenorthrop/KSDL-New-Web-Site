<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title><g:layoutTitle default="Kagyu Samye Dzong London" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <lsd:link obj="${application}"/>
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>

        <div class="logo"><img src="${resource(dir:'images',file:'logo.png')}" alt="Kagyu Samye Dzong London Logo" /><h1 style="margin-left:20px;">Kagyu Samye Dzong London</h1></div>

        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Welcome</a></span>
            <span class="menuButton"><a class="news" href="${resource(dir:'')}">News</a></span>
            <span class="menuButton"><a class="events" href="${resource(dir:'')}">Events</a></span>
            <span class="menuButton"><a class="wellbeing" href="${resource(dir:'')}">Wellbeing</a></span>
            <span class="menuButton"><a class="community" href="${resource(dir:'')}">Community</a></span>
            <span class="menuButton"><a class="help" href="${resource(dir:'')}">Help</a></span>
        </div>
        <g:layoutBody />
    </body>
</html>