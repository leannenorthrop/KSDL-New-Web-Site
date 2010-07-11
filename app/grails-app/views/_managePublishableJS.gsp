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
  Time: 1:02:32 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<jq:jquery>
  var currentTabIndex = 0;
  var currentTabDiv;
  var tabsId = "#${tabsId}";
    $('a.step').live('click', function() {
      $(tabsId).tabs('url', currentTabIndex, this.href);
      $(currentTabDiv).load(this.href);
      return false;
    });
    $('a.nextLink').live('click', function() {
      $(tabsId).tabs('url', currentTabIndex, this.href);
      $(currentTabDiv).load(this.href);
      return false;
    });
    $('a.prevLink').live('click', function() {
      $(tabsId).tabs('url', currentTabIndex, this.href);
      $(currentTabDiv).load(this.href);
      return false;
    });
    $('th.sortable a').live('click', function() {
      $(tabsId).tabs('url', currentTabIndex, this.href);
      $(currentTabDiv).load(this.href);
      return false;
    });
    $(tabsId).tabs({
      fx: { opacity: 'toggle' },
      select: function(event, ui) {
          currentTabDiv = $(ui.panel);
          currentTabIndex = $(tabsId).tabs('option', 'selected');
        },
        load: function(event, ui) {
          currentTabDiv = $(ui.panel);
          currentTabIndex = $(tabsId).tabs('option', 'selected');
        }      
      
    });
</jq:jquery>
