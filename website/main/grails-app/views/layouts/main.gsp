<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>Kagyu Samye Dzong London <g:layoutTitle /></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<![if gte IE 7]>
    <link rel="stylesheet" media="screen, projection" href="${resource(dir:'css/site',file:'screen.css')}" />
    <link rel="stylesheet" media="screen, projection" href="${resource(dir:'css/themes/pastel',file:'screen.css')}" />
<![endif]>
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
    </head>
    <body class="${controllerName}">
    <shiro:isLoggedIn in="['Editor','Administrator','Author']">
        <body style="min-width:70em">
    </shiro:isLoggedIn>
    <shiro:isNotLoggedIn><body></shiro:isNotLoggedIn>
        <lsdc:header />
        <lsdc:nav current="${controllerName}"/>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>
        <g:layoutBody/>
        <lsdc:grid />
        <div class="footer">Copyright &#169; 2009</div>
    </body>
</html>
