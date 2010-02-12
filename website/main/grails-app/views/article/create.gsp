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
<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="article.create.title"/></title>
    <g:javascript>
      $(function() {
        $("#createarticle").validate();
        $("#content-tabs").tabs({
          fx: { opacity: 'toggle' },
          select: function(event, ui) {
              if (ui.panel.id == 'preview-tab') {
                  var text = $('#content').val();
                  $('#previewcontenttxt').attr("value",text);
                  var form = $('#previewcontent');
                  var url = form.attr("action");
                  var dataString = form.serialize();
                  $.post(url,dataString,function(data) {
                          $('#preview-tab').html(data);
                  });
              }
            }
        });
      });
    </g:javascript>
  </head>
  <body>
    <g:form id="createarticle" name="createarticle" action="save" method="post">
      <h1 class="ui-widget-header"><g:message code="article.create.title"/></h1>

      <g:if test="${flash.message && !flash.isError}">
        <p class="ui-widget ui-state-highlight ui-corner-all">
          <strong><span class="ui-icon ui-icon-info" style="display: inline-block"></span><g:message code="info"/></strong><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/>
        </p>
      </g:if>
      <g:elseif test="${flash.message && flash.isError}">
        <g:set var="errorsList"><g:renderErrors bean="${articleInstance}" as="list"></g:renderErrors></g:set>
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

      <fieldset>
        <label for="title"><g:message code="article.title.label"/></label>
        <g:textField name="title" value="${fieldValue(bean:articleInstance,field:'title')}" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'title','errors')}" minlength="5"/>
      </fieldset>
      <fieldset>
        <label for="image.id"><g:message code="article.image.label"/></label>
        <g:set var="noImgLabel"><g:message code="no.img"/></g:set>
        <g:select from="${org.samye.dzong.london.media.Image.findAllByTag('article')}" name="image.id" value="${articleInstance?.image?.id}" noSelection="${['null':noImgLabel]}" optionKey="id" optionValue="name"/>
      </fieldset>
      <fieldset>
        <label for="category"><g:message code="event.category.label"/></label>
        <g:select name="category" from="${articleInstance.constraints.category.inList}" value="${articleInstance?.category}" valueMessagePrefix="publish.category" class="required ui-corner-all ${hasErrors(bean:event,field:'title','errors')}"/>
      </fieldset>
      <fieldset>
        <label for="summary"><g:message code="article.summary.label"/></label>
        <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'summary','errors')}" value="${fieldValue(bean:articleInstance,field:'summary')}" minlength="5"/>
      </fieldset>
      <fieldset class="last">
        <div id="content-tabs">
            <ul>
                <li><a href="#edit-tab"><g:message code="edit.tab.title" default="Edit"/></a></li>
                <li><a href="${createLink(controller: 'help', action: 'textile')}" title="Hints Tab"><g:message code="hints.tab.title" default="Hints"/></a></li>
                <li><a href="#preview-tab"><g:message code="preview.tab.titile" default="Preview"/></a></li>
            </ul>
            <div id="edit-tab">
                <label for="content"><g:message code="article.content.label"/></label>
                <g:textArea rows="35" cols="40" name="content" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'content','errors')}" value="${fieldValue(bean:articleInstance,field:'content')}" minlength="5"/>
            </div>
            <div id="Hints_Tab">
            </div>
            <div id="preview-tab">
            </div>
        </div>
      </fieldset>
      <g:set var="createSubmitBtnLabel"><g:message code="article.create.btn"/></g:set>
      <g:submitButton name="submitbtn" value="${createSubmitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
    </g:form>
    <div style="display:none;">
        <g:form name="previewcontent" action="preview" method="post">
            <g:textArea rows="35" cols="40" name="previewcontenttxt" value="${fieldValue(bean:articleInstance,field:'content')}"/>
        </g:form>
    </div>
  </body>
</html>
