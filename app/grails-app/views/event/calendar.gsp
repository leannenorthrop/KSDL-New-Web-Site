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
  User: Leanne Northrop
  Date: Jan 29, 2010,5:01:51 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.Setting" %>
<html>
  <head>
    <title>calendar</title>
    <meta name="layout" content="main">
    <g:javascript src="jquery/jquery-1.4.2.min.js"/>
    <g:javascript src="jquery/jquery-ui-1.8.2.min.js"/>
    <g:javascript src="jquery/fullcalendar/fullcalendar.js"/>  
    <link rel="stylesheet" type="text/css" media="screen, projection" href="${resource(dir: 'css/site/fullcalendar', file: 'fullcalendar.css')}"/>    
<g:if test="${params.theme}">
    <g:set var="cssThemesDir" value="${params.theme}"/>
</g:if>
<g:else>
    <g:set var="cssThemesDir" value="${Setting.findByName('DefaultTheme').value}"/>
</g:else>
    <link rel="stylesheet" type="text/css" media="screen, projection" href="${resource(dir: 'css/themes/' + cssThemesDir, file: 'colours.css')}"/>
    <g:javascript>
    $(document).ready(function() { 
        $('#calendar').fullCalendar({ 
            header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
            editable: false,
			events: "events",
            aspectRatio: 1.25
        })
    });
    </g:javascript>
  </head>
  <body>
    <div class="grid_16">
        <div id='calendar' style="width: 1000px; margin: 0 auto;min-height:1000px"></div>
    </div>
  </body>
</html>
