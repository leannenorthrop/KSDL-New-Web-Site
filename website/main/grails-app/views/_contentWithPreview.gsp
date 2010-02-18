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
  Created by IntelliJ IDEA.
  User: northrl
  Date: Feb 18, 2010
  Time: 1:20:27 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div id="content-tabs">
  <ul>
    <li><a href="#edit-tab"><g:message code="edit.tab.title" default="Edit"/></a></li>
    <li><a href="${createLink(controller: 'manageSite', action: 'textile')}" title="Hints Tab"><g:message code="hints.tab.title" default="Hints"/></a></li>
    <li><a href="#preview-tab"><g:message code="preview.tab.titile" default="Preview"/></a></li>
  </ul>
  <div id="edit-tab">
    <label for="content"><g:message code="article.content.label"/></label>
    <g:textArea rows="35" cols="40" name="content" class="ui-corner-all ${hasErrors(bean:publishableInstance,field:'content','errors')}" value="${fieldValue(bean:publishableInstance,field:'content')}"/>
  </div>
  <div id="Hints_Tab">
  </div>
  <div id="preview-tab">
  </div>
</div>
<g:javascript>
  $("#content-tabs").tabs({
    fx: { opacity: 'toggle' },
    select: function(event, ui) {
      if (ui.panel.id == 'preview-tab') {
        var text = $('#content').val();
        var url = '${createLink(controller: previewController, action: 'preview')}';
        var dataString = 'previewcontenttxt=' + text;
        $.post(url, dataString, function(data) {
          $('#preview-tab').html(data);
        });
      }
    }
  });
</g:javascript>
