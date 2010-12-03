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
    <title><g:message code="manage.articles.title"/></title>
  <g:set var="tabsId" value="articles-tabs"/>
  <g:render template="/managePublishableJS" model="[tabsId: tabsId]"/>
</head>
<body>
  <form>
    <fieldset>
      <g:render template="/managePublishable" model="[tabsId: tabsId]"/>
      <shiro:hasAnyRole in="${['Author','Admin']}">
        <p class="last">&nbsp;</p>
        <g:actionSubmit value="${message(code:'add.article.btn')}" action="create" class="ui-corner-all"/>
      </shiro:hasAnyRole>
    </fieldset>
  </form>
</body>
</html>
