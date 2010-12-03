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
    <g:textField name="title" value="${fieldValue(bean:articleInstance,field:'title')}" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'title','error')}" minlength="5"/>
    </p>
    <lsdc:selectImg obj="${articleInstance}" tag="${'article'}"/>
    <p>
      <label for="category"><g:message code="event.category.label"/></label>
    <g:select name="category" from="${['M','N','C','W','B','A','H','S']}" value="${articleInstance?.category}" valueMessagePrefix="publish.category" class="required ui-corner-all ${hasErrors(bean:event,field:'category','errors')}"/>
    </p>
    <p>
      <label for="summary"><g:message code="article.summary.label"/><em>Textile may be used. See <g:link controller="manageSite" action='textile' target="_blank">Textile</g:link> for details.</em></label>
    <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'summary','error')}" value="${articleInstance?.summary}" minlength="5"/>
    </p>
  </fieldset>
  <fieldset>
    <legend>Content</legend>
    <g:render template="/contentWithPreview" model="[previewController: 'article',publishableInstance:articleInstance]"/>
    <p class="last">&nbsp;</p>
    <g:submitButton name="submitbtn" value="${message(code:'article.create.btn')}" id="submitbtn" class="ui-corner-all"/>
    <input type="button" class="ui-corner-all cancel" value="${message(code:'cancel')}" onclick="window.history.back();"/>
  </fieldset>
</g:form>
</body>
</html>
