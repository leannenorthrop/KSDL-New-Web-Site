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
    <title><g:message code="article.edit.title" args="${[articleInstance?.title]}"/></title>
    <g:javascript>
      $(function() {
        $("#updatearticle").validate();
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
    <g:form name="updatearticle" method="post" action="update">
      <g:hiddenField name="id" value="${articleInstance?.id}"/>
      <g:hiddenField name="version" value="${articleInstance?.version}"/>
      <g:hiddenField name="publishState" value="${articleInstance?.publishState}"/>
      <g:hiddenField name="deleted" value="${articleInstance?.deleted}"/>
      <g:hiddenField name="displayAuthor" value="${articleInstance?.displayAuthor}"/>
      <g:hiddenField name="displayDate" value="${articleInstance?.displayDate}"/>
      <fieldset>
            <legend>Details</legend>
      <p>
        <label for="title"><g:message code="article.title.label"/></label>
        <g:textField name="title" value="${fieldValue(bean:articleInstance,field:'title')}" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'title','error')}" minlength="5"/>
      </p>
        <span style="float:left;width: 14em;">
            <p>
              <label for="image.id" style="display:inline-block;width:6em;"><g:message code="article.image.label"/></label>
              <g:set var="noImgLabel"><g:message code="no.img"/></g:set>
              <g:select from="${org.samye.dzong.london.media.Image.findAllByTag('article')}" name="image.id" value="${articleInstance?.image?.id}" noSelection="${['null':noImgLabel]}" optionKey="id" optionValue="name" class="ui-corner-all"/>
            </p>
            <p>
              <label for="category" style="display:inline-block;width:6em;"><g:message code="article.category.label"/></label>
              <g:select name="category" from="${['M','N','C','W','B','A','H','S']}" value="${articleInstance?.category}" valueMessagePrefix="publish.category" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'category','errors')}"/>
            </p>
        </span>
        <span style="float:left;margin-left:1.2em;min-width: ${org.samye.dzong.london.site.Setting.findByName('ThumbSize').value}">
            <lsdc:thumbnail srcid="${articleInstance?.image?.id}" id="thumb_image"/>
        </span>
        <span class="clear"></span>  
      <p>
        <label for="summary"><g:message code="article.summary.label"/><em>Textile may be used. See <g:link controller="manageSite" action='textile' target="_blank">Textile</g:link> for details.</em></label>
        <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:articleInstance,field:'summary','error')}" value="${articleInstance.summary}" minlength="5"/>
      </p>
      </fieldset>
      <fieldset>
        <legend>Content</legend>
        <g:render template="/contentWithPreview" model="[previewController: 'manageSite',publishableInstance:articleInstance]"/>
        <p class="last">&nbsp;</p>
        <span style="float:left;width: 75%;">
            <g:set var="submitBtnLabel"><g:message code="updatearticle.btn"/></g:set>
            <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
            <shiro:hasRole name="Editor">
                <g:actionSubmit value="${message(code:'updateandpubarticle.btn')}" action="updateAndPublish" class="ui-corner-all"/>
            </shiro:hasRole>        
            <input type="button" class="ui-corner-all cancel" value="${message(code:'cancel')}" onclick="window.history.back();"/>   
        </span>
        <span style="float:right;width: 24%;">    
            <g:actionSubmit value="${message(code:'article.delete.btn')}" action="delete" class="ui-corner-all" style="float:right;"/>
        </span>
      </fieldset>
    </g:form>
      <fieldset>
          <legend>Comments</legend>    
          <lsdc:comments bean="${articleInstance}" />                   
      </fieldset> 
  </body>
</html>
