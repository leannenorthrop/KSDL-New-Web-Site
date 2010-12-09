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
    Meditation Home Page
    User: Leanne Northrop
    Date: Jan 24, 2010, 2:00:21 PM
--%>
<%@ page import="org.samye.dzong.london.site.Link" contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title><g:message code="room.home.title"/></title>
    <meta name="layout" content="main">
  </head>
  <body>
      <div class="grid_4">
          <g:render template="/roomNav" model="[venues:venues,allArticles:allArticles]"/>
      </div>
          
    <div class="grid_12">
      <g:render template="/toparticles" model="[articles:homeArticles,displayTitle:false]"/>
      <div class="container_16">
            <div class="grid_8">
              <g:render template="/articlelist" model="[articles:[featuredRooms[0]], controller: 'room', action:'view']"/>
            </div>

            <div class="grid_8">
              <g:render template="/articlelist" model="[articles:[featuredRooms[1]], controller: 'room', action:'view']"/>
            </div>          
      </div>
    </div>

    <div class="clear"></div>      
  </body>
</html>
