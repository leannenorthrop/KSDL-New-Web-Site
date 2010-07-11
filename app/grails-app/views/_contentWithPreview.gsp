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
  User: Leanne Northrop
  Date: Feb 18, 2010, 1:20:27 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div id="content-tabs">
  <ul>
    <li><a href="#edit-tab"><g:message code="edit.tab.title" default="Edit"/></a></li>
    <li><a href="#preview-tab" ><g:message code="preview.tab.title" default="Preview"/></a></li>    
    <li><a href="${createLink(controller: 'manageSite', action: 'textile')}" title="Hints Tab"><g:message code="hints.tab.title" default="Hints"/></a></li>
  </ul>
  <div id="edit-tab" style="height:45em;overflow-y:scroll;">
    <lsdc:remoteField name="content" controller="manageSite" action="preview" update="preview-tab" paramName="previewcontenttxt" class="ui-corner-all ${hasErrors(bean:publishableInstance,field:'content','errors')}" value="${publishableInstance?.content}"/>
  </div>
  <div id="preview-tab" style="height:45em;overflow-y:scroll;">
  </div>
  <div id="Hints_Tab" style="height:45em;overflow-y:scroll;">
  </div>
</div>

<g:javascript>
  $("#content-tabs").tabs({
    fx: { opacity: 'toggle' }
  });
  $("#edit-tab textarea").blur();
</g:javascript>
