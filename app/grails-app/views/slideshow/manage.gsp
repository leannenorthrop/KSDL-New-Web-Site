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
  Browse images in the system. All images are published.
  User: Leanne Northrop
  Date: Jun 13, 2010, 3:03:58 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="slideshow.list.title"/></title>
  </head>
  <body>
    <form>
        <fieldset>
                <ul class="images ui-widget ui-widget-content ui-corner-all">
                  <g:each in="${albums}" status="i" var="album">
                    <li>
                      <g:link action="edit" id="${album.albumId}" style="width:130px;height:130px;text-align:center;vertical-align:middle;">
                        <img src="${album?.src}" alt="${album?.name}" title="${album?.name}" class="ui-widget-content ui-corner-all ui-shadow" style="float:none;"/>
                      </g:link>
                    </li>
                  </g:each>
                </ul>
                <p class="last">&nbsp;</p>
                <g:actionSubmit value="${message(code:'update.slideshow.btn')}" action="updateCache" class="ui-corner-all"/>
        </fieldset>
    </form>
  </body>
</html>
