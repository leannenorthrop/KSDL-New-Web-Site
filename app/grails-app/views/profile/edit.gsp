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
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title><g:message code="profile.edit.title"/></title>       
        <g:javascript>
          $(function() {
            $("#edit").validate();
          });
        </g:javascript>         
    </head>
    <body>
        <div class="container_16">
            <div class="grid_3">
                <g:if test="${user.profile?.image && user.profile?.mimeType?.endsWith('png')}">
                  <img src="${createLink(controller: 'profile', action: 'src', id: user.id)}" title="${user.profile?.publicName}" alt="${user.profile?.publicName}" class="pngImg" style="max-width:100%"/>
                </g:if>
                <g:else>
                  <img src="${createLink(controller: 'profile', action: 'src', id: user.id)}" title="${user.profile?.publicName}" alt="${user.profile?.publicName}" style="max-width:100%"/>
                </g:else>                
            </div>
            <div class="grid_13">
                <g:uploadForm name="edit" action="save">
                  <g:render template="/messageBox" model="[flash: flash]"/>
                      <fieldset>
                          <legend><g:message code="profile.edit.title"/></legend>
                          <p>
                            <label for="name"><g:message code="profile.publicName.label"/></label>
                            <input type="text" id="publicName" name="publicName" class="required ${hasErrors(bean: user.profile, field: 'publicName', 'errors')}" min-length="5" value="${fieldValue(bean: user.profile, field: 'publicName')}"/>
                          </p>
                          <p>
                            <label for="name"><g:message code="profile.nickName.label"/></label>
                            <input type="text" id="nickName" name="nickName" class="required ${hasErrors(bean: user.profile, field: 'nickName', 'errors')}" min-length="5" value="${fieldValue(bean: user.profile, field: 'nickName')}"/>
                          </p>                          
                          <p>
                            <label for="file"><g:message code="profile.image.file"/> <strong>Images larger than 75 pixels x 75 pixels will be cropped and scaled.</strong></label>
                            <input type="file" id="image" name="image"/>
                          </p>
                          <p>
                            <label for="tags"><g:message code="profile.permissions"/></label>
                            ${user.roles?.join(", ")}
                          </p>
                          <p class="last"></p>
                          <g:set var="submitBtnLabel"><g:message code="profile.submit.btn"/></g:set>
                          <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
                      </fieldset>
                </g:uploadForm>                
            </div>            
        </div>
    </body>
</html>