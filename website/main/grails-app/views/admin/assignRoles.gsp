<html>
    <head>
        <title><g:message code='role.perm.title' default="Manage UserPermissions"/></title>
        <meta name="layout" content="content-admin" />
    </head>
<body>
  <g:if test="${flash.message}">
    <g:set var="minHeightEm" value="${10+(users.size()*3)}em;"/>
  </g:if>
  <g:else>
    <g:set var="minHeightEm" value="${5+(users.size()*3)}em;"/>
  </g:else>
  <g:form name="roles" action="assignRoles" style="min-height:${minHeightEm}">
    <h1 class="ui-widget-header"><g:message code='role.perm.title' default="Manage UserPermissions"/></h1>

    <g:if test="${flash.message}">
      <g:if test="${flash.isError}">
      <p class="ui-widget ui-state-error ui-corner-all">
      <strong><span class="ui-icon ui-icon-alert" style="display: inline-block"></span><g:message code="alert"/></strong><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/>
      </p>
      </g:if>
      <g:else>
      <p class="ui-widget ui-state-highlight ui-corner-all">
      <strong><span class="ui-icon ui-icon-info" style="display: inline-block"></span><g:message code="info"/></strong><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/>
      </p>
      </g:else>
    </g:if>

    <table>
        <thead>
            <tr>
            <th><g:message code='role.perm.username.col.title'/></th>
            <g:each var="role" in="${roles}">
            <th><g:message code='role.${role.name}'/></th>
            </g:each>
            </tr>
        </thead>
        <tbody>
        <g:each in="${users}" status="i" var="user">
        <tr style="min-height:15em;">
        <td>${user.username}</td>
        <g:each var="role" in="${roles}">
        <g:set var="checkboxName" value="${user.id + '-' +role.name}"/>
        <g:set var="avalue" value="${Boolean.FALSE}"/>
        <g:findAll in="${user.roles}" expr="${it.name.contains(role.name)}">
        <g:set var="avalue" value="${Boolean.TRUE}"/>
        </g:findAll>
        <td><g:checkBox name="${checkboxName}" value="${avalue}" /></td>
        </g:each>
        </tr>
        </g:each>
        </tbody>
    </table>

    <g:set var="submitBtnLabel"><g:message code="role.perm.submit"/></g:set>
    <g:submitButton name="rolePermSubmitBtn" value="${submitBtnLabel}" id="requestRolesBtn" class="ui-corner-all"/>

  </g:form>
</body>
</html>
