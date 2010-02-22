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
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title><g:message code="meditation.home.title"/></title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="grid_12">
      <g:render template="/toparticles" model="[articles:topArticles]"/>
    </div>
    <div class="grid_4">
      <div class="box">
        <h2>Video</h2>
      </div>
      <div class="box">
        <h2><g:message code="meditation.resources"/></h2>
        <ol>
          <li><g:link action="all"><g:message code="meditation.articles"/></g:link></li>
        </ol>
      </div>
    </div>
    <div class="clear"></div>

    <div class="grid_5">
      <g:render template="/articlelist" model="[articles:meditationArticles,controller:'meditation',action:'view',total:total,moreAction:'all',heading:'meditation.articles.title']"/>
    </div>
    <div class="grid_11">
      <div class="box">
        <h2><g:message code="event.meditation"/></h2>
        <g:render template="/eventlist" model="[events: events]"/>
      </div>
    </div>
  </body>
</html>
