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
  Created by IntelliJ IDEA.
  User: northrl
  Date: Feb 18, 2010
  Time: 4:54:10 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<p class="group">
  <label for="tags"><g:message code="article.tag.label"/> <strong><g:message code="article.tag.warning"/></strong></label>
  <textarea cols="5" rows="5" id="tags" name="tags" class="${hasErrors(bean: articleInstance, field: 'tags', 'errors')} required" minlength="3">${articleInstance.tags.join(",")}</textarea>
  <p class="tags_help">
    <g:message code="article.tag.help"/>
  </p>
</p>
<p>
  <label for="displayAuthor" title="Display author's public name"><g:message code="article.display.author"/>
      <g:checkBox name="displayAuthor" checked="${articleInstance?.displayAuthor}"/> 
  </label>
  <label for="displayDate"><g:message code="article.display.date"/>
  <g:if test="${'N' == articleInstance?.category}">
    <g:checkBox name="displayDate" checked="${true}"/>
  </g:if>
  <g:else>
   <g:checkBox name="displayDate" checked="${articleInstance?.displayDate}"/>
  </g:else>
    <label for="home" title="Show on Home Page for News Artcles or at top Top of Section Page."><g:message code="article.display.home"/>
        <g:checkBox name="home" checked="${articleInstance?.home}"/>       
    </label>
    <label for="featured" title="Show as first article of the Section Page according to the Category selected above."/><g:message code="article.display.featured"/>
        <g:checkBox name="featured" checked="${articleInstance?.featured}"/>   
    </label>
  </label>
</p>
