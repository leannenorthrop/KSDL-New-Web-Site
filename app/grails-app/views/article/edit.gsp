%{------------------------------------------------------------------------------
     Copyright © 2010 Leanne Northrop

     This file is part of Samye Content Management System.

     Samye Content Management System is free software: you can redistribute it
     and/or modify it under the terms of the GNU General Public License as
     published by the Free Software Foundation, either version 3 of the License,
     or (at your option) any later version.

     Samye Content Management System is distributed in the hope that it will be
     useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with Samye Content Management System.
     If not, see <http://www.gnu.org/licenses/>.

     BT plc, hereby disclaims all copyright interest in the program
     “Samye Content Management System” written by Leanne Northrop.
  ----------------------------------------------------------------------------}%
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="article.edit.title" args="${[publishableInstance?.title]}"/></title>
  <g:javascript>
    $(function() {
    $("#updatearticle").validate();
    });
  </g:javascript>
</head>
<body>
<g:form name="updatearticle" method="post" action="update">
  <g:hiddenField name="id" value="${publishableInstance?.id}"/>
  <g:hiddenField name="version" value="${publishableInstance?.version}"/>
  <g:hiddenField name="publishState" value="${publishableInstance?.publishState}"/>
  <g:hiddenField name="deleted" value="${publishableInstance?.deleted}"/>
  <g:hiddenField name="displayAuthor" value="${publishableInstance?.displayAuthor}"/>
  <g:hiddenField name="displayDate" value="${publishableInstance?.displayDate}"/>
  <fieldset>
    <legend>Details</legend>
    <p>
      <label for="title"><g:message code="article.title.label"/></label>
    <g:textField name="title" value="${fieldValue(bean:publishableInstance,field:'title')}" class="required ui-corner-all ${hasErrors(bean:publishableInstance,field:'title','error')}" minlength="5"/>
    </p>

    <lsdc:selectImg obj="${publishableInstance}" tag="${'article'}"/>

    <p>
      <label for="category" style="display:inline-block;width:6em;"><g:message code="article.category.label"/></label>
    <g:select name="category" from="${['M','N','C','W','B','A','H','S']}" value="${publishableInstance?.category}" valueMessagePrefix="publish.category" class="required ui-corner-all ${hasErrors(bean:publishableInstance,field:'category','errors')}"/>
    </p>
    <p>
      <label for="summary"><g:message code="article.summary.label"/><em>Textile may be used. See <g:link controller="manageSite" action='textile' target="_blank">Textile</g:link> for details.</em></label>
    <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:publishableInstance,field:'summary','error')}" value="${publishableInstance.summary}" minlength="5"/>
    </p>
  </fieldset>
  <fieldset>
    <legend>Content</legend>
    <g:render template="/contentWithPreview" model="[previewController: 'manageSite',publishableInstance:publishableInstance]"/>
    <p class="last">&nbsp;</p>
    <span style="float:left;width: 75%;">
      <g:set var="submitBtnLabel"><g:message code="updatearticle.btn"/></g:set>
      <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
      <shiro:hasRole name="Editor">
        <g:actionSubmit value="${message(code:'updateandpubarticle.btn')}" action="updateAndPublish" class="ui-corner-all"/>
      </shiro:hasRole>
      <input type="button" class="ui-corner-all cancel" value="${message(code:'cancel')}" onclick="window.history.back();"/>
    </span>
  </fieldset>
</g:form>
<fieldset>
  <legend>Comments</legend>
  <lsdc:comments bean="${publishableInstance}" />
</fieldset> 
</body>
</html>
