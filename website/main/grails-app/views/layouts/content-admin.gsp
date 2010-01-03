<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>Kagyu Samye Dzong London <g:layoutTitle default="" /></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<![if gte IE 7]>
    <link rel="stylesheet" media="screen, projection" href="${resource(dir:'css/themes/default',file:'screen.css')}" />
<![endif]>
<!--[if gte IE 7]>
    <link rel="stylesheet" media="screen, projection" href="${resource(dir:'css/themes/default',file:'ie.css')}" />
<[endif]-->
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <lsd:link obj="${application}"/>
        <g:javascript library="jquery"/>
        <g:layoutHead />
    </head>
    <body style="min-width:50em">
        <lsdc:header />
        <lsdc:nav current="manageSite"/>
        <lsdc:toolbar controller="${controllerName}" action="${actionName}" id="${id}"/>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>
        <g:layoutBody />
        <lsdc:grid />        
        <div class="footer">Copyright &#169; 2009</div>       
    </body>
</html>
