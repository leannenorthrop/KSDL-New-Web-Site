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
  Home Page for About Us
  User: Leanne Northrop
  Date: Feb 12, 2010, 5:43:08 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title><g:message code="aboutUs"/></title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="grid_12">
      <g:render template="/toparticles" model="[articles:topArticles,displayTitle:false]"/>
    </div>

    <div class="grid_4">
      <div class="box">
        <h2><g:message code="footer.help"/></h2>
        <ul>
          <li><g:message code="footer.visit.us"/></li>
          <li><g:message code='footer.contact.us'/></li>
        </ul>
      </div>
    </div>

    <div class="clear"></div>

    <div class="grid_8">
      <g:render template="/articlelist" model="[articles:lineageTeachers, heading: 'teacher.category.L', controller: 'teacher', action:'view']"/>
    </div>

    <div class="grid_8">
      <g:render template="/articlelist" model="[articles:teachers, heading: 'teacher.heading.title', controller: 'teacher', action:'view']"/>
    </div>

  </body>
</html>
