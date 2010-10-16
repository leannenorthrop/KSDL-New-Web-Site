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
  Date: Jun 13, 2010, 3:40:00 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="content_admin_mootools"/>          
    <title><g:message code="slideshow.edit.title"/></title>
    <link rel="stylesheet" type="text/css" media="screen, projection" href="${resource(dir: 'css/site/slideshow', file: 'slideshow.css')}"/>         
  </head>
  <body>
      <div class="container_16">
          <div class="grid_4">
              <g:form name="save" action="save" method="post">
                  <g:hiddenField name="id" value="${id}"/>
                  <fieldset>
                    <legend><g:message code="slideshow.select.title" default="Slide Show Settings"/></legend>

                    	<p>You may choose to display this slideshow on one of the section index pages by selecting an below:<br/><br/>

                          <g:radioGroup name="location" labels="${['None', 'Home', 'Meditation','Buddhism']}" values="${['SSNone', 'SSHome', 'SSMeditation','SSBuddhism']}" value="${'SSNone'}" >
                          <p>${it.label}: ${it.radio}</p>
                          </g:radioGroup>
                    	</p>

                    <p class="last"></p>
                    <g:submitButton name="create" class="ui-corner-all" value="${message(code: 'event.save.btn', default: 'Save Changes')}"/>
                </fieldset>
              </g:form>
          </div>
          <div class="grid_12" style="min-height:600px">
              <g:render template="/slideshow" model="[album:album]"/>
          </div>
          <div class="clear"></div>       
      </div>
  </body>
</html>
