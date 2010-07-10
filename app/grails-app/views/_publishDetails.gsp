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
  <label for="displayAuthor"><g:message code="article.display.author"/>
  <g:if test="${articleInstance?.displayAuthor}">
      <input type="checkbox" name="displayAuthor" id="displayAuthor" checked="true"></input>    
  </g:if>
  <g:else>
      <input type="checkbox" name="displayAuthor" id="displayAuthor"></input>
  </g:else>  
  </label>
  <label for="displayDate"><g:message code="article.display.date"/>
  <g:if test="${'N' == articleInstance?.category}">
    <input type="checkbox" name="displayDate" id="displayDate" checked="true"></input>
  </g:if>
  <g:else>
    <g:if test="${articleInstance?.displayDate}">
        <input type="checkbox" name="displayDate" id="displayDate" checked="true"></input>    
    </g:if>
    <g:else>
        <input type="checkbox" name="displayDate" id="displayDate"></input>
    </g:else>
  </g:else>
    <label for="home"><g:message code="article.display.home"/>
        <g:if test="${articleInstance?.home}">
            <input type="checkbox" name="home" id="home" checked="true"></input>    
        </g:if>
        <g:else>
            <input type="checkbox" name="home" id="home"></input>
        </g:else>        
    </label>
    <label for="featured"><g:message code="article.display.featured"/>
    <g:if test="${articleInstance?.featured}">
        <input type="checkbox" name="featured" id="featured" checked="true"></input>    
    </g:if>
    <g:else>
        <input type="checkbox" name="featured" id="featured"></input>
    </g:else>    
    </label>
  </label>
</p>
