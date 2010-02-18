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
  Date: Jan 30, 2010
  Time: 7:00:41 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <title>${teacher?.title} ${teacher?.name}</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <div class="col1_80_Percent article">
            <h2><g:message code="${'teacher.title.' + teacher?.title}"/> ${teacher?.name}</h2>

            <div class="body group">
                <img src="${createLink(controller: 'image', action: 'src', id: teacher?.image.id)}" title="${teacher?.image.name}" alt="${teacher?.image.name}" style="float:right;"/>
                ${teacher.content.encodeAsTextile()}
            </div><!-- /body -->
        </div><!-- /left -->

        <div class="col2_20_Percent">
        </div>
    </body>
</html>