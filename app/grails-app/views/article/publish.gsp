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
      <g:render template="/messageBox" model="[flash: flash]"/>

      <g:hiddenField name="id" value="${articleInstance?.id}"/>
      <g:hiddenField name="version" value="${articleInstance?.version}"/>
      <g:hiddenField name="deleted" value="${false}"/>
      <g:hiddenField name="publishState" value="Published"/>

      <shiro:hasAnyRole in="['Author']">
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
          <label for="category"><g:message code="article.category.label"/></label>
          <g:select name="category" from="${['M','N','C','W','B']}" value="${articleInstance?.category}" valueMessagePrefix="publish.category" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'title','errors')}"/>
        </p>
        <p>
          <label for="summary"><g:message code="article.summary.label"/><em>Textile may be used. See <g:link controller="help" action='textile'>Textile</g:link> for details.</em></label>
          <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'summary','errors')}" value="${articleInstance.summary}" minlength="5"/>
        </p>
        </fieldset>
        <fieldset>
          <legend>Content</legend>
          <g:render template="/contentWithPreview" model="[previewController: 'article',publishableInstance:articleInstance]"/>
        </fieldset>
      </shiro:hasAnyRole>
      <fieldset>
          <legend>Publication Options</legend>
          <g:render template="/publishDetails" model="[articleInstance:articleInstance]"/>
      </fieldset>
      <p class="last">&nbsp;</p>
      <g:set var="submitBtnLabel"><g:message code="article.publish.btn"/></g:set>
      <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
    </g:form>
  </body>
</html>
