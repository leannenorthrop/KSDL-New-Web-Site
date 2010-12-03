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
  Template for content administration pages.
  User: Leanne
  Date: Jan 24, 2010, 2:00:21 PM
--%>
<%@ page import="org.samye.dzong.london.media.Image" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.site.Setting" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title><g:layoutTitle/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="ROBOTS" content="NOINDEX, NOFOLLOW,NOARCHIVE">
    <link rel="stylesheet" media="screen, projection" href="${resource(dir: 'css/site', file: 'screen.css')}"/>
    <link rel="stylesheet" media="screen, projection" href="${resource(dir: 'css/manage', file: 'screen.css')}"/>
    <link rel="stylesheet" type="text/css" media="screen, projection" href="${resource(dir: 'css/site/slideshow', file: 'slideshow.css')}"/>     
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <g:javascript library="mootools/mootools"/>
    <g:javascript library="mootools/mootools-more"/>
    <g:javascript library="mootools/mootools-fluid16"/>
    <g:javascript library="mootools/slideshow"/>     
    <g:javascript library="mootools/slideshow.kenburns"/>
    <g:layoutHead/>
  </head>
  <body class="contentAdmin" style="min-width:70em"> 
      <lsdc:toolbar controller="${controllerName}" action="${actionName}" id="${id}"/>
        <div id="panel">
            <div>
                <h1><g:layoutTitle/></h1>
                <g:render template="/messageBox" model="[flash: flash]"/>
            </div>
        </div>   
      
    <div class="container_16 content">      
        <g:layoutBody/>
    </div>
    <div class="clear"></div>

    <div class="grid_16">
        <div class="footer">
            <ul>
                <li>
                    <g:set var="year"><g:formatDate format="yyyy" date="${new Date()}"/></g:set>
                    <g:message code="footer.copyright" args="${[year]}"/> Leanne Northrop
                </li>
                <li>
                    Version <g:meta name="app.version"/> Built with Grails <g:meta name="app.grails.version"/>
                </li>
                <li>
                    ${(Runtime.getRuntime().freeMemory() / 1024) / 1024} Mb free of ${(Runtime.getRuntime().totalMemory() / 1024) / 1024} Mb
                </li>
            </ul>
        </div>
    </div>
    <div class="clear"></div>
    </div>
  </body>
</html>

