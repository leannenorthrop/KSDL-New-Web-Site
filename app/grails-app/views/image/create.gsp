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
  Create an embedded image in the system. All images are published.
  User: Leanne Northrop
  Date: Jan 25, 2010, 6:04:58 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="image.create.title"/></title>
    <g:javascript>
      $(function() {
        $("#addimage").validate();
        $("#src-tabs").tabs({fx: { opacity: 'toggle' }});
      });
    </g:javascript>
  </head>
  <body>
    <g:uploadForm name="addimage" action="save">
      <g:hiddenField name="thumbnail" value=""/>
      <g:render template="/messageBox" model="[flash: flash]"/>

      <div id="src-tabs">
        <ul>
          <li><a href="#upload"><g:message code="image.embed.title"/></a></li>
          <li><a href="#flickr"><g:message code="image.flickr.title"/></a></li>
        </ul>
        <div id="upload">
          <fieldset>
            <label for="name"><g:message code="image.name"/></label>
            <input type="text" id="name" name="name" class="required ${hasErrors(bean: imageInstance, field: 'name', 'errors')}" min-length="5" value="${fieldValue(bean: imageInstance, field: 'name')}"/>
          </fieldset>
          <fieldset>
            <label for="file"><g:message code="image.create.file"/></label>
            <input type="file" id="image" name="image"/>
          </fieldset>
          <fieldset class="last">
            <label for="tags"><g:message code="article.tag.label"/> <strong><g:message code="article.tag.warning"/></strong></label>
            <g:textArea rows="5" cols="40" name="tags" class="required ui-corner-all ${hasErrors(bean:imageInstance,field:'tags','errors')}" minlength="5">${imageInstance.tags.join(",")}</g:textArea>
            <p class="tags_help">
              <g:message code="img.tag.help"/>
            </p>
          </fieldset>
          <g:set var="submitBtnLabel"><g:message code="image.create.submit.btn"/></g:set>
          <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
        </div>
        <div id="flickr">
          <h1>Coming Soon</h1>
        </div>
      </div>
    </g:uploadForm>
  </body>
</html>
