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
  Date: Jan 25, 2010, 6:04:58 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
    <head>
        <meta name="layout" content="content-admin"/>
        <title><g:message code="image.list.title"/></title>
    </head>
    <body>
        <form>
            <fieldset>
                <ul class="images">
                    <g:each in="${imageInstanceList}" status="i" var="imageInstance">
                    <li>
                        <g:link action="edit" id="${imageInstance.id}" style="width:130px;height:130px;text-align:center;vertical-align:middle;">
                        <img src="${createLink(controller: 'image', action: 'thumbnail', id: imageInstance.id)}" alt="${fieldValue(bean: imageInstance, field: 'name')}" title="${fieldValue(bean: imageInstance, field: 'name')}" class="ui-widget-content ui-corner-all ui-shadow" style="float:none;"/>
                        <!--span>${fieldValue(bean: imageInstance, field: 'name')}</span-->
                        </g:link>
                    </li>
                    </g:each>
                </ul>

                <p class="last manage paginateButtons">
                    <g:paginate total="${imageInstanceTotal}"/>
                </p>
                
                <g:actionSubmit value="${message(code:'image.add.submit.btn')}" action="create" class="ui-corner-all"/>
            </fieldset>
        </form>    
    </body>
</html>
