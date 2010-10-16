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
  Date: Jan 28, 2010,10:14:44 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="teacher.create.title"/></title>
    <g:javascript>
      $(function() {
        $("#addteacher").validate();
        $("#image\\.id").change(function() {
            var src = $("option:selected", this).val();
            var href = '${createLink(controller: 'image', action:'thumbnail', id:'0')}';
            $("#thumb_image").attr("srcid",src);
            $("#thumb_image").attr("src",href.replace('0',src));
        });        
      });
    </g:javascript>
  </head>
  <body>
    <g:form id="addteacher" name="addteacher" action="save" method="post">
        <g:hiddenField name="publishState" value="Unpublished"/>
        <g:hiddenField name="deleted" value="false"/>
        <g:hiddenField name="displayAuthor" value="false"/>
        <g:hiddenField name="displayDate" value="false"/>
        <g:hiddenField name="home" value="false"/>
        <g:hiddenField name="featured" value="false"/>
        <g:hiddenField name="category" value="T"/>    
        
        <fieldset>
            <legend>Details</legend>

            <p>
                <label for="title"><g:message code="teacher.title.label"/></label>
                <g:set var="noneLabel"><g:message code="no.img"/></g:set>
                <g:select name="title" from="${['V','L','R','M','MS','MZ','MSS','K','HH','HE','HS']}" noSelection="${['U':noneLabel]}" valueMessagePrefix="teacher.title" />
            </p>
            
            <p>
                <label for="name"><g:message code="teacher.name.label"/></label>
                <g:textField name="name" value="${fieldValue(bean:teacher,field:'name')}" class="required ui-corner-all ${hasErrors(bean:teacher,field:'name','errors')}" minlength="5"/>
            </p>
            
            <span style="float:left;width: 14em;min-height:${org.samye.dzong.london.Setting.findByName('ThumbSize').value}px">
                <p>
                  <label for="image.id" style="display:inline-block;width:6em;"><g:message code="teacher.image.label"/></label>
                  <g:set var="noImgLabel"><g:message code="no.img"/></g:set>
                  <g:select from="${org.samye.dzong.london.media.Image.findAllByTag('teacher')}" name="image.id" value="${teacher?.image?.id}" noSelection="${['null':noImgLabel]}" optionKey="id" optionValue="name" class="ui-corner-all"/>
                </p>
            </span>
            <span style="float:left;margin-left:1.2em;min-width: ${org.samye.dzong.london.Setting.findByName('ThumbSize').value}px; min-height:${org.samye.dzong.london.Setting.findByName('ThumbSize').value}px">
                <lsdc:thumbnail srcid="${teacher?.image?.id}" id="thumb_image"/>
            </span>
            <span class="clear"></span>  
            
            <p>
                <label for="category"><g:message code="teacher.category"/></label>
                <g:select name="type" from="${['L', 'C', 'V', 'O','T']}" valueMessagePrefix="teacher.category" />
            </p>
            
            <p>
                <label for="summary"><g:message code="teacher.summary.label"/></label>
                <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:teacher,field:'summary','errors')}" value="${fieldValue(bean:teacher,field:'summary')}" minlength="5"/>
            </p>
            </fieldset>
            <fieldset>            
                <legend>Content</legend>
                <g:render template="/contentWithPreview" model="[previewController: 'manageSite',publishableInstance:teacher]"/>
            
                <p class="last">&nbsp;</p>
                <g:set var="submitBtnLabel"><g:message code="teacher.create.btn"/></g:set>
                <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
            </fieldset>
        </fieldset>
    </g:form>
  </body>
</html>
