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
<%@ page import="org.samye.dzong.london.site.Link" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="manage.links.title" default="Manage Link Menus"/></title>
    <jq:jquery>
      var currentTabIndex = 0;
      var currentTabDiv;
      var tabsId = "#links";
        $(tabsId).tabs({
          fx: { opacity: 'toggle' }
        });
    </jq:jquery>    
  </head>
  <body>
      <div id="links">
        <ul>
          <li><a href="homeMenu"><g:message code="links.home" default="Home"/></a></li>
          <li><a href="meditationMenu"><g:message code="links.meditation" default="Meditation"/></a></li>
          <li><a href="buddhismMenu"><g:message code="links.buddhism" default="Buddhism"/></a></li>                    
        </ul>
      </div>
  </body>
</html>
