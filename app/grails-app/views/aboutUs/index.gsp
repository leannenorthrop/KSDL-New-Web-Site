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
Home Page for About Us
User: Leanne Northrop
Date: Feb 12, 2010, 5:43:08 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title><g:message code="aboutUs"/></title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="grid_4">
      <div class="box">
        <h3><g:message code="footer.help"/></h3>
        <ul class="menu">
          <li class="menuitem"><g:link controller="aboutUs" action="contactUs"><g:message code="footer.contact.us"/></g:link></li>
          <li class="menuitem"><g:link controller="aboutUs" action="visiting"><g:message code="footer.visit.us"/></g:link></li>
          <li class="menuitem"><g:link controller="aboutUs" action="roomHire"><g:message code="footer.room.hire"/></g:link></li>           
          <li class="menuitem">Teachers/Course Leaders
          <ul>
            <li class="menuitem"><g:link controller="aboutUs" action="lineage"><g:message code="teacher.lineage.heading.title"/></g:link></li>
            <li class="menuitem"><g:link controller="aboutUs" action="teachers"><g:message code="teacher.center.heading.title"/></g:link></li>
          </ul>
          </li>
          <li class="menuitem">Locations
            <ul>
              <g:if test="${venues}">
                <g:each var="venue" in="${venues}">
                  <li class="menuitem"><g:link controller="aboutUs" action="venue" id="${venue?.id}" class="menuitem">${venue}</g:link>
                  <g:if test="${venue.rooms}">
                    <ul>
                      <g:findAll var="room" in="${venue.rooms}" expr="it.publishState == 'Published'">
                        <li class="menuitem"><g:link controller="aboutUs" action="room" id="${room.id}" class="menuitem">${room}</g:link></li>
                      </g:findAll>
                    </ul>
                  </g:if>
                  </li>
                </g:each>          
              </g:if>
            </ul>
          </li>
          <li class="menuitem">Information
            <ul>
              <g:if test="${articles}">
                <g:each var="article" in="${featuredArticles}">
                  <li class="menuitem"><g:link controller="aboutUs" action="view" id="${article.id}" class="menuitem">${article?.title}</g:link></li>
                </g:each>
              </g:if>
            </ul>
          </li>
        </ul>
      </div>
    </div>
          
    <div class="grid_12">
      <g:render template="/toparticles" model="[articles:homeArticles,displayTitle:false]"/>
      <div class="container_16">
            <div class="grid_8">
              <g:render template="/articlelist" model="[articles:visitingTeachers, heading: 'Visiting Teachers &amp; Course Leaders', controller: 'aboutUs', action:'teacher']"/>
            </div>

            <div class="grid_8">
              <g:render template="/articlelist" model="[articles:teachers, heading: 'Center Teachers &amp; Course Leaders', controller: 'aboutUs', action:'teacher']"/>
            </div>          
      </div>      
    </div>

    <div class="clear"></div>

  </body>
</html>
