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
<html>
<head>
    <title><g:message code="rss.title"/></title>
    <meta name="layout" content="main">
</head>
<body>
<div class="col1_80_Percent box">
    <h1><g:message code="rss.heading"/></h1>
    <p><g:message code="rss.msg"/>
        <ol>
            <g:each in="${['news','meditation','wellbeing','community']}" var="feed">
            <li class="rss">
              <g:set var="a">
                <g:link controller="feed" action="${feed}"><g:message code="${feed}"/></g:link>
              </g:set>
              <g:message code="${'rss.'+feed}" args="${[a]}"/>
            </li>
            </g:each>
        </ol>
    </p>
</div>
<div class="col2_20_Percent">
    <lsdc:cloud />
</div>
</body>
</html>
