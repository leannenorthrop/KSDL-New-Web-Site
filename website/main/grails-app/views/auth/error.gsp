<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>Error</title>
        <meta name="layout" content="content-admin" />
        <link rel="stylesheet" href="${resource(dir:'css/redmond',file:'jquery-ui-1.7.2.custom.css')}" media="screen, projection" />
    </head>
<body>
    <div class="ui-widget ui-state-error ui-corner-all">
        <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
        <strong>Alert:</strong> ${flash.message}</p>
    </div>
</body>
</html>

