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
    List of About Us articles
    User: Leanne Northrop
    Date: 9th December 2010, 16:58
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title><g:message code="buddhism.all.articles.title"/></title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="grid_4">
        <g:render template="/aboutUsNav"/>
    </div>
          
    <div class="grid_12">      
      <g:render template="/articlelist" model="[articles:allArticles,controller:'aboutUs',action:'view',moreAction:'all']"/>
    </div>
  </body>
</html>