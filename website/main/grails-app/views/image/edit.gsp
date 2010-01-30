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
  Edit an image in the system. All images are published.
  User: Leanne Northrop
  Date: Jan 25, 2010, 6:04:58 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="image.edit.title"/></title>
    <g:javascript>
      $(function() {
        $("#imageform").validate();
      });
    </g:javascript>
  </head>
  <body>
    <g:form name="imageform" action="update" method="post">
      <h1 class="ui-widget-header"><g:message code="image.edit.title"/></h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${imageInstance}">
        <div class="errors">
          <g:renderErrors bean="${imageInstance}" as="list"/>
        </div>
      </g:hasErrors>
      <input type="hidden" name="id" value="${imageInstance?.id}"/>
      <input type="hidden" name="version" value="${imageInstance?.version}"/>
      <input type="hidden" name="name" value="${imageInstance?.name}"/>
      <h2>${fieldValue(bean: imageInstance, field: 'name')}</h2>
      <img src="${createLink(controller: 'image', action: 'src', id: imageInstance.id)}" style="max-width: 350px;"/>
      <fieldset class="last">
        <label for="tags"><g:message code="article.tag.label"/> <strong><g:message code="article.tag.warning"/></strong></label>
        <g:textArea rows="5" cols="40" name="tags" class="ui-corner-all ${hasErrors(bean:articleInstance,field:'tags','errors')}" minlength="5">${imageInstance.tags.join(",")}</g:textArea>
        <p class="tags_help">
          <g:message code="img.tag.help"/>
        </p>
      </fieldset>
      <g:set var="submitBtnLabel"><g:message code="image.edit.submit.btn"/></g:set>
      <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
    </g:form>
  </body>
</html>
