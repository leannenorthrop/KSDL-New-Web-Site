<html>
    <head>
        <title><g:message code='req.perm.title'/></title>
        <meta name="layout" content="content-admin" />
    </head>
<body>
  <g:form name="requestRoles" controller="admin" action="requestRoles" class="ui-widget ui-corner-all" style="height: ${roles.size()*2}em">
    <h1 class="ui-widget-header"><g:message code='req.perm.title'/></h1>
    <g:hiddenField name="id" value="${user?.passwordReset}" />
    <g:hiddenField type="hidden" name="reset" value="${user?.passwordReset}" />

    <p><g:message code='req.perm.msg'/></p>

    <ul>
        <g:each var="role" in="${roles}">
            <li><g:checkBox name="${'role.' + role.name}" /><g:message code='role.${role.name}'/></li>
        </g:each>
    </ul>
    <g:set var="submitBtnLabel"><g:message code="req.perm.submit"/></g:set>
    <g:submitButton name="requestRolesBtn" value="${submitBtnLabel}" id="requestRolesBtn" class="ui-corner-all"/>
  </g:form>
</body>
</html>
