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
<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="image.show.title" args="${[fieldValue(bean:imageInstance,field:'name')]}"/></title>
  </head>
  <body>
    <div class="container_16">
      <div class="grid_8">
        <img src="${createLink(controller: 'image', action: 'src', id: imageInstance.id)}" style="max-width: 200px"/>
      </div>
      <div class="grid_8">
        <form>
          <fieldset>
            <legend>Details for ${fieldValue(bean:imageInstance,field:'name')}</legend>

            <p><g:message code="image.label.msg"/>
            <ul>
              <g:each in="${imageInstance.tags}" status="i" var="tag">
                <li>${tag}</li>
              </g:each>
            </ul>
            </p>
          </fieldset>
        </form>
      </div>
    </div>
  </body>
</html>
