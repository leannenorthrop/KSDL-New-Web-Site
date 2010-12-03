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
    @author Leanne Northrop
    @since Feb 18, 2010,  4:54:10 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" import="org.samye.dzong.london.events.Event" %>
<p class="group">
  <label for="tags"><g:message code="article.tag.label"/> <strong><g:message code="article.tag.warning"/></strong></label>
  <textarea cols="5" rows="5" id="tags" name="tags" class="${hasErrors(bean: articleInstance, field: 'tags', 'errors')} required" minlength="3">${articleInstance.tags.join(",")}</textarea>
  <span class="tags_help">
    <g:message code="article.tag.help"/>
  </span>
</p>
<p>
  <label for="displayAuthor" title="Display author's public name"><g:message code="article.display.author"/>
      <g:checkBox name="displayAuthor" checked="${articleInstance?.displayAuthor}"/> 
  </label>
</p>
<p>
  <label for="displayDate"><g:message code="article.display.date"/></label>
  <g:checkBox name="displayDate" checked="${articleInstance?.displayDate}"/>
</p>
<p>
  <span id="is_home">
      <label for="home" title="Show on Home Page"><span><g:message code="article.display.home"/></span>
          <g:checkBox name="home" checked="${articleInstance?.home}"/>       
      </label>
  </span>
</p>
<p>
  <span id="is_featured">
      <label for="featured"><span><g:message code="article.display.section"/></span>
          <g:checkBox name="featured" checked="${articleInstance?.featured}"/>       
      </label>
  </span>
</p>
<g:javascript>
    var isEvent = ${articleInstance instanceof Event};
    var featuredLabel = "${message(code:'article.display.section')}";
    var homeLabel = "${message(code:'article.display.home')}";
</g:javascript>
<g:javascript src='site/publish.js'/>

