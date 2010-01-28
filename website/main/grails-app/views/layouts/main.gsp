<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>Kagyu Samye Dzong London <g:layoutTitle /></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

            <!--link rel="stylesheet" media="screen, projection" href="${resource(dir:'css/themes/pastel',file:'screen.css')}" /-->
<![if gte IE 7]>
    <link rel="stylesheet" media="screen, projection" href="${resource(dir:'css/site',file:'screen.css')}" />
    <lsd:cssTheme app="${application}"/>
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
        <div class="watermark">
            <span class="one"/>
            <span class="two"/>
            <span class="three"/>
            <span class="four"/>
        </div>
        <div class="services">
            Services E-mail News News Feeds iCalendar Schedule
        </div>
        <div class="footer">Copyright &#169; <g:formatDate format="yyyy" date="${new Date()}"/> |
                            <g:link controller="home" action="aboutUs">About Us</g:link> |
                            <g:link controller="home" action="contactUs">Contact Us</g:link> |
                            <g:link controller="home" action="help">Help</g:link> |
                            <g:link controller="home" action="changeCssTheme">Change Theme</g:link> |
                            <g:link controller="home" action="siteMap">Site Map</g:link> |
                            <g:link controller="home" action="aboutThisSite">About This Site</g:link>
        </div>
    </body>
</html>
