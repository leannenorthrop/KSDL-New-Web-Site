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
    <title><g:message code="teacher.edit.title"/></title>
    <g:javascript>
      $(function() {
        $("#editteacher").validate();
      });
    </g:javascript>
  </head>
  <body>
    <g:form id="editteacher" name="editteacher" action="save" method="post">
      <h1 class="ui-widget-header"><g:message code="teacher.edit.title"/></h1>

      <g:if test="${flash.message && !flash.isError}">
        <p class="ui-widget ui-state-highlight ui-corner-all">
          <strong><span class="ui-icon ui-icon-info" style="display: inline-block"></span><g:message code="info"/></strong><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/>
        </p>
      </g:if>
      <g:elseif test="${flash.message && flash.isError}">
        <g:set var="errorsList"><g:renderErrors bean="${teacher}" as="list"></g:renderErrors></g:set>
        <div class="ui-widget ui-state-error ui-corner-all">
          <strong>
            <span class="ui-icon ui-icon-alert" style="display: inline-block"></span><g:message code="alert"></g:message>
          </strong>
          <g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"></g:message>
          ${errorsList}
        </div>
      </g:elseif>

      <g:hiddenField name="publishState" value="Unpublished"/>
      <g:hiddenField name="deleted" value="false"/>
      <g:hiddenField name="displayAuthor" value="false"/>
      <g:hiddenField name="displayDate" value="false"/>
      <g:hiddenField name="id" value="${articleInstance?.id}"/>
      <g:hiddenField name="version" value="${articleInstance?.version}"/>

      <fieldset>
        <label for="title"><g:message code="teacher.title.label"/></label>
        <g:select name="title" from="${['V','L','R','M','MS','MZ','MSS','K','HH','HE','HS']}" value="${fieldValue(bean:teacher,field:'title')}" valueMessagePrefix="teacher.title" />
      </fieldset>
      <fieldset>
        <label for="name"><g:message code="teacher.name.label"/></label>
        <g:textField name="name" value="${fieldValue(bean:teacher,field:'name')}" class="required ui-corner-all ${hasErrors(bean:teacher,field:'name','errors')}" minlength="5"/>
      </fieldset>
      <fieldset>
        <label for="image.id"><g:message code="teacher.image.label"/></label>
        <g:set var="noImgLabel"><g:message code="no.img"/></g:set>
        <g:select from="${org.samye.dzong.london.media.Image.list()}" name="image.id" value="${teacher?.image?.id}" noSelection="${['null':noImgLabel]}" optionKey="id" optionValue="name"/>
      </fieldset>
      <fieldset>
        <label for="category"><g:message code="teacher.category"/></label>
        <g:select name="category" from="${['L', 'C', 'V', 'O']}" valueMessagePrefix="teacher.category" value="${fieldValue(bean:teacher,field:'category')}" />
      </fieldset>
      <fieldset>
        <label for="summary"><g:message code="teacher.summary.label"/></label>
        <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:teacher,field:'summary','errors')}" value="${fieldValue(bean:teacher,field:'summary')}" minlength="5"/>
      </fieldset>
      <fieldset class="last">
        <label for="content"><g:message code="teacher.content.label"/></label>
        <g:textArea rows="35" cols="40" name="content" class="required ui-corner-all ${hasErrors(bean:teacher,field:'content','errors')}" value="${fieldValue(bean:teacher,field:'content')}" minlength="5"/>
      </fieldset>
      <g:set var="submitBtnLabel"><g:message code="teacher.save.btn"/></g:set>
      <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
    </g:form>
  </body>
</html>
