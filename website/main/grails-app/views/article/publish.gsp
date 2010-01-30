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
    <title><g:message code="article.publish.title" args="${[fieldValue(bean:articleInstance,field:'title')]}"/></title>
    <g:javascript>
      $(function() {
        $("#publish").validate();
      });
    </g:javascript>
  </head>
  <body>
    <g:form name="publish" action="publish" method="post">
      <h1 class="ui-widget-header"><g:message code="article.publish.heading" args="${[fieldValue(bean:articleInstance,field:'title')]}"/></h1>

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

      <g:hiddenField name="id" value="${articleInstance?.id}"/>
      <g:hiddenField name="version" value="${articleInstance?.version}"/>
      <g:hiddenField name="deleted" value="${false}"/>
      <g:hiddenField name="publishState" value="Published"/>

      <shiro:hasAnyRole in="['Author']">
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
          <label for="category"><g:message code="article.category.label"/></label>
          <g:select name="category" from="${articleInstance.constraints.category.inList}" value="${articleInstance?.category}" valueMessagePrefix="publish.category" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'title','errors')}"/>
        </fieldset>
        <fieldset>
          <label for="summary"><g:message code="article.summary.label"/><em>Textile may be used. See <g:link controller="help" action='textile'>Textile</g:link> for details.</em></label>
          <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'summary','errors')}" value="${fieldValue(bean:articleInstance,field:'summary')}" minlength="5"/>
        </fieldset>
        <fieldset>
          <label for="content"><g:message code="article.content.label"/></label>
          <g:textArea rows="35" cols="40" name="content" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'content','errors')}" value="${fieldValue(bean:articleInstance,field:'content')}" minlength="5"/>
        </fieldset>
      </shiro:hasAnyRole>
      <fieldset class="group">
        <label for="tags"><g:message code="article.tag.label"/> <strong><g:message code="article.tag.warning"/></strong></label>
        <textarea cols="5" rows="5" id="tags" name="tags" class="${hasErrors(bean: articleInstance, field: 'tags', 'errors')} required" minlength="3">${articleInstance.tags.join(",")}</textarea>
        <p class="tags_help">
          <g:message code="article.tag.help"/>
        </p>
      </fieldset>
      <fieldset class="last">
        <label for="displayAuthor"><g:message code="article.display.author"/>
        <g:checkBox name="displayAuthor" value="${articleInstance?.displayAuthor}"/>
        </label>
        <label for="displayDate"><g:message code="article.display.date"/>
        <g:if test="${articleInstance?.publishState != 'Published'}">
          <g:checkBox name="displayDate" checked="${true}"/>
        </g:if>
        <g:else>
          <g:checkBox name="displayDate" value="${articleInstance?.displayDate}"/>
        </g:else>
        </label>
      </fieldset>
      <g:set var="submitBtnLabel"><g:message code="article.publish.btn"/></g:set>
      <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
    </g:form>
  </body>
</html>
