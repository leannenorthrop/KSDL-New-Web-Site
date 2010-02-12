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
  All meditation articles
  User: Leanne
  Date: Feb 12, 2010, 21:59:21 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title><g:message code="community.title"/></title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="col1_80_Percent">
      <div class="box">
        <g:render template="/toparticles" model="[articles:topArticles]"/>
      </div>
      <div>
        <div class="col1_50_Percent">
          <g:render template="/articlelist" model="[articles:community,controller:'community',action:'view',total:totalVolunteer]"/>
        </div>
        <div class="col2_50_Percent">
          <g:render template="/articlelist" model="[articles:volunteerOpportunities,controller:'community',action:'view',total:totalCommunity]"/>
        </div>
      </div>
    </div>
    <div class="col2_20_Percent">
      <!--div class="box">
        Slide Show
      </div>
      <div class="box">
        Volunteer Form
      </div-->
    </div>
  </body>
</html>
