<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title><g:message code="title" default="Kagyu Samye Dzong London:"/> <g:layoutTitle /></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<![if gte IE 7]>
    <link rel="stylesheet" media="screen, projection" href="${resource(dir:'css/site',file:'screen.css')}" />
    <link rel="stylesheet" media="screen, projection" href="${resource(dir:'css/manage',file:'screen.css')}" />
    <lsd:cssTheme app="${application}"/>
<![endif]>
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:javascript library="jquery"/>
        <g:javascript src="jquery/jquery-ui-1.7.2.custom.min.js"/>
        <g:javascript src="jquery/jquery.validate.min.js"/>
        <g:layoutHead />
    </head>
    <body class="contentAdmin" style="min-width:60em">
        <lsdc:header />
        <lsdc:nav current="manageSite"/>
        <lsdc:toolbar controller="${controllerName}" action="${actionName}" id="${id}"/>
        <!--div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div-->
        <div class="content">
            <g:layoutBody />
        </div>
        <lsdc:grid />
        <div class="footer">Copyright &#169; <g:formatDate format="yyyy" date="${new Date()}"/></div>
    </body>
</html>
