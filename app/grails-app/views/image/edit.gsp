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
    <title><g:message code="image.edit.title" args="${[fieldValue(bean:imageInstance,field:'name')]}"/></title>
  <g:javascript>
    $(function() {
    $("#imageform").validate();
    });
  </g:javascript>
</head>
<body>
  <div class="container_16">
    <div class="grid_8">
      <g:if test="${imageInstance?.mimeType.endsWith('png')}">
        <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: imageInstance.id)}" title="${imageInstance.name}" alt="${imageInstance.name}" class="pngImg"/>
      </g:if>
      <g:else>
        <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: imageInstance.id)}" title="${imageInstance.name}" alt="${imageInstance.name}"/>
      </g:else>
    </div>
    <div class="grid_8">
      <g:form name="imageform" action="update" method="post">
        <g:hiddenField name="id" value="${imageInstance?.id}"/>
        <g:hiddenField name="version" value="${imageInstance?.version}"/>
        <g:hiddenField name="name" value="${imageInstance?.name}"/>

        <fieldset>
          <legend><g:message code="image.tag"/></legend>
          <p>
            <label for="tags"><g:message code="article.tag.label"/> <strong><g:message code="article.tag.warning"/></strong></label>
          <g:textArea rows="5" cols="40" name="tags" class="required ui-corner-all ${hasErrors(bean:imageInstance,field:'tags','errors')}" minlength="5">${imageInstance.tags.join(",")}</g:textArea>
          </p>
          <p class="tags_help">
          <g:message code="img.tag.help"/>
          </p>
          <p class="last"></p>
          <g:set var="submitBtnLabel"><g:message code="image.edit.submit.btn"/></g:set>
          <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
        </fieldset>
      </g:form>
    </div>
  </div>
</body>
</html>
