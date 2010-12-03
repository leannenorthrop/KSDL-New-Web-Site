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
  <g:hiddenField name="id" value="${articleInstance?.id}"/>
  <g:hiddenField name="version" value="${articleInstance?.version}"/>
  <g:hiddenField name="deleted" value="${false}"/>
  <g:hiddenField name="publishState" value="Published"/>

  <shiro:hasRole name="Author">
    <fieldset>
      <legend>Details</legend>
      <p>
        <label for="title"><g:message code="article.title.label"/></label>
      <g:textField name="title" value="${fieldValue(bean:articleInstance,field:'title')}" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'title','error')}" minlength="5"/>
      </p>
      <lsdc:selectImg obj="${articleInstance}" tag="${'article'}"/>
      <p>
        <label for="category" style="display:inline-block;width:6em;"><g:message code="article.category.label"/></label>
      <g:select name="category" from="${['M','N','C','W','B','A','H','S']}" value="${articleInstance?.category}" valueMessagePrefix="publish.category" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'category','errors')}"/>
      </p>
      <p>
        <label for="summary"><g:message code="article.summary.label"/><em>Textile may be used. See <g:link controller="manageSite" action='textile' target="_blank">Textile</g:link> for details.</em></label>
      <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'summary','error')}" value="${articleInstance.summary}" minlength="5"/>
      </p>
    </fieldset>
    <fieldset>
      <legend>Content</legend>
      <g:render template="/contentWithPreview" model="[previewController: 'article',publishableInstance:articleInstance]"/>
    </fieldset>
  </shiro:hasRole>
  <shiro:lacksRole name="Author">
    <g:render template="/article" model="[articleInstance: articleInstance, articles:[]]"/>
  </shiro:lacksRole>
  <fieldset>
    <legend>Publication Options</legend>
    <g:render template="/publishDetails" model="[articleInstance:articleInstance]"/>
    <p class="last">&nbsp;</p>
    <g:set var="submitBtnLabel"><g:message code="article.publish.btn"/></g:set>
    <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
    <g:actionSubmit value="${message(code:'cancel')}" action="manage" class="ui-corner-all cancel"/>
  </fieldset>
</g:form>
<fieldset>
  <legend>Comments</legend>
  <lsdc:comments bean="${articleInstance}" />
</fieldset>    
</body>
</html>
