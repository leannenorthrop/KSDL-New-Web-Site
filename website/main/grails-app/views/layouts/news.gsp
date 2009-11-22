<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title><g:layoutTitle default="Kagyu Samye Dzong London" /></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <!--[if gte IE 7]-->
            <link rel="stylesheet" media="screen, projection" href="${resource(dir:'css/themes/default',file:'screen.css')}" />
        <!--[endif]-->

        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <lsd:link obj="${application}"/>
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>
        <lsdc:header />
        <div class="news">
            <lsdc:nav />
            <g:layoutBody />
        </div>
        <lsdc:grid />
    </body>
</html>