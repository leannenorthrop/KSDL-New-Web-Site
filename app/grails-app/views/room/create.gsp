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
  Template for adding teachers.

  User: Leanne Northrop
  Date: Jun 14, 2010,3:11:44 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="room.create.title"/></title>
    <g:javascript>
      $(function() {
        $("#addroom").validate();
      });
    </g:javascript>
  </head>
  <body>
    <g:form id="addroom" name="addroom" action="save" method="post">
        <g:hiddenField name="publishState" value="Unpublished"/>
        <g:hiddenField name="deleted" value="false"/>
        <g:hiddenField name="displayAuthor" value="false"/>
        <g:hiddenField name="displayDate" value="false"/>
        <g:hiddenField name="home" value="false"/>
        <g:hiddenField name="featured" value="false"/>
        <g:hiddenField name="category" value="R"/>    
        
        <fieldset>
            <legend>Details</legend>
            <g:render template="/messageBox" model="[flash: flash]"/>
            <p>
                <label for="name"><g:message code="room.name.label"/></label>
                <g:textField name="name" value="${fieldValue(bean:room,field:'name')}" class="required ui-corner-all ${hasErrors(bean:room,field:'name','errors')}" minlength="5"/>
            </p>
            
            <p>
                <label for="image.id"><g:message code="room.image.label"/></label>
                <g:set var="noImgLabel"><g:message code="no.img"/></g:set>
                <g:select from="${org.samye.dzong.london.media.Image.findAllByTag('room')}" name="image.id" value="${room?.image?.id}" noSelection="${['null':noImgLabel]}" optionKey="id" optionValue="name"/>
            </p>
            
            <p>
                <label for="venue.id"><g:message code="room.venue.label"/></label>
                <g:select from="${org.samye.dzong.london.venue.Venue.findAll()}" name="venue.id" value="${room?.venue?.id}" optionKey="id" optionValue="name"/>
            </p>
            
            <p>
                <label for="forHire.id"><g:message code="room.forHire.label"/></label>
                <g:checkBox name="forHire" value="${room?.forHire}" checked="${room?.forHire}"/>
            </p>
                        
            <p>
                <label for="summary"><g:message code="room.summary.label"/></label>
                <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:room,field:'summary','errors')}" value="${fieldValue(bean:room,field:'summary')}" minlength="5"/>
            </p>
            </fieldset>
            <fieldset>            
                <legend>Content</legend>
                <g:render template="/contentWithPreview" model="[previewController: 'manageSite',publishableInstance:room]"/>
            
                <p class="last">&nbsp;</p>
                <g:set var="submitBtnLabel"><g:message code="room.create.submit.btn"/></g:set>
                <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
            </fieldset>
        </fieldset>
    </g:form>
  </body>
</html>
