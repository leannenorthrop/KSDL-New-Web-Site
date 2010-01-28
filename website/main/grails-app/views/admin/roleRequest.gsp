<html>
    <head>
        <title>Request Permissions</title>
        <meta name="layout" content="content-admin" />
    </head>
<body>
  <g:form name="requestRoles" controller="admin" action="requestRoles" class="ui-widget ui-corner-all" style="height: 18em">
    <h1 class="ui-widget-header">Request Permission Rights</h1>
    <g:hiddenField name="id" value="${user?.passwordReset}" />
    <g:hiddenField type="hidden" name="reset" value="${user?.passwordReset}" />

    <p>Hi ${user.username}, please select from the list below the permissions you would like to have. Once you have completed this please click the Send Now button.</p>

    <ul>
        <g:each var="role" in="${roles}">
            <li><g:checkBox name="${'role.' + role.name}" />${role.name}</li>
        </g:each>
    </ul>
    <g:submitButton name="requestRolesBtn" value="Request Roles »" id="requestRolesBtn" class="ui-corner-all"/>
  </g:form>
</body>
</html>
