<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>Manage User Roles</title>
        <meta name="layout" content="content-admin" />
    </head>
<body>
  <g:form name="roles" action="assignRoles">
    <h1>Manage User Roles</h1>
    <g:if test="${flash.message}">
    <label class="message">${flash.message}</label>
    </g:if>

    <table>
        <thead>
            <tr>
            <th>User</th>
            <g:each var="role" in="${roles}">
            <th>${role.name}</th>
            </g:each>
            </tr>
        </thead>
        <tbody>
        <g:each in="${users}" status="i" var="user">
        <tr>
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

    <a class="submit" onClick="document.roles.submit();">Save &raquo;</a>
  </g:form>
</body>
</html>
