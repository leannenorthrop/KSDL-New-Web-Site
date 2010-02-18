%{------------------------------------------------------------------------------
  - Copyright © 2010 Leanne Northrop
  -
  - This file is part of Samye Content Management System.
  -
  - Samye Content Management System is free software: you can redistribute it
  - and/or modify it under the terms of the GNU General Public License as
  - published by the Free Software Foundation, either version 3 of the License,
  - or (at your option) any later version.
  -
  - Samye Content Management System is distributed in the hope that it will be
  - useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
  - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  - GNU General Public License for more details.
  -
  - You should have received a copy of the GNU General Public License
  - along with Samye Content Management System.
  - If not, see <http://www.gnu.org/licenses/>.
  -
  - BT plc, hereby disclaims all copyright interest in the program
  - “Samye Content Management System” written by Leanne Northrop.
  ----------------------------------------------------------------------------}%

<%--
  Change article publication details.
  User: Leanne Northrop
  Date: Feb 18, 2010,4:52:14 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="article.alter.title" args="${[fieldValue(bean:articleInstance,field:'title')]}"/></title>
  </head>
  <body>
    <g:form name="publish" action="updatePublished" method="post">
      <h1 class="ui-widget-header"><g:message code="article.alter.title" args="${[fieldValue(bean:articleInstance,field:'title')]}"/></h1>

      <g:render template="/messageBox" model="[flash: flash]"/>

      <g:hiddenField name="id" value="${articleInstance?.id}"/>
      <g:hiddenField name="version" value="${articleInstance?.version}"/>
      <g:hiddenField name="deleted" value="${false}"/>
      <g:hiddenField name="publishState" value="Published"/>

      <g:render template="/publishDetails" model="[articleInstance:articleInstance]"/>

      <g:set var="submitBtnLabel"><g:message code="article.publish.btn"/></g:set>
      <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
    </g:form>
  </body>
</html>



