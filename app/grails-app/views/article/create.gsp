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
      });
    </g:javascript>
  </head>
  <body>
    <g:form id="createarticle" name="createarticle" action="save" method="post">
      <g:render template="/messageBox" model="[flash: flash]"/>

      <g:hiddenField name="publishState" value="Unpublished"/>
      <g:hiddenField name="deleted" value="false"/>
      <g:hiddenField name="displayAuthor" value="false"/>
      <g:hiddenField name="displayDate" value="false"/>
      <g:hiddenField name="home" value="false"/>
      <g:hiddenField name="featured" value="false"/>

      <fieldset>
          <legend>Details</legend>
      <p>
        <label for="title"><g:message code="article.title.label"/></label>
        <g:textField name="title" value="${fieldValue(bean:articleInstance,field:'title')}" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'title','errors')}" minlength="5"/>
      </p>
      <p>
        <label for="image.id"><g:message code="article.image.label"/></label>
        <g:set var="noImgLabel"><g:message code="no.img"/></g:set>
        <g:select from="${org.samye.dzong.london.media.Image.findAllByTag('article')}" name="image.id" value="${articleInstance?.image?.id}" noSelection="${['null':noImgLabel]}" optionKey="id" optionValue="name"/>
      </p>
      <p>
        <label for="category"><g:message code="event.category.label"/></label>
        <g:select name="category" from="${['M','N','C','W','B','A','H']}" value="${articleInstance?.category}" valueMessagePrefix="publish.category" class="required ui-corner-all ${hasErrors(bean:event,field:'title','errors')}"/>
      </p>
      <p>
        <label for="summary"><g:message code="article.summary.label"/></label>
        <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'summary','errors')}" value="${articleInstance?.summary}" minlength="5"/>
      </p>
      </fieldset>
      <fieldset>
          <legend>Content</legend>
          <g:render template="/contentWithPreview" model="[previewController: 'article',publishableInstance:articleInstance]"/>
          <p class="last">&nbsp;</p>
          <g:set var="createSubmitBtnLabel"><g:message code="article.create.btn"/></g:set>
          <g:submitButton name="submitbtn" value="${createSubmitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
      </fieldset>
    </g:form>
  </body>
</html>
