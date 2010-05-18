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

<%@ page import="org.samye.dzong.london.venue.Room" %>
<%@ page import="org.samye.dzong.london.venue.Venue" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title><g:message code="venue.manage.title"/></title>
        <g:javascript>
                $(function() {
                    $("#venues").tabs();
                    <g:each in="${venueInstanceList}" status="i" var="venueInstance">
                    $("#tabs-${i} .contactDetails").tabs();
                    $("#tabs-${i} .findUsDetails").tabs();
                    $("#tabs-${i} .roomDetails").tabs();
                    </g:each>
                });
        </g:javascript>
    </head>
    <body>
        <div class="jquery-ui content">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>

            <g:if test="${venueInstanceList}">
            <div id="venues">
                <ul>
                    <g:each in="${venueInstanceList}" status="i" var="venueInstance">
                    <li><a href="#tabs-${i}">${fieldValue(bean:venueInstance, field:'name')}</a></li>
                    </g:each>
                </ul>
                <g:each in="${venueInstanceList}" status="i" var="venueInstance">
                <div id="tabs-${i}">
                    <g:if test="${venueInstance.image}">
                    <img src="${createLink(controller: 'image', action: 'src', id: venueInstance.image.id)}" title="${venueInstance.image.name}" alt="${venueInstance.image.name}"/>
                    </g:if>
                    <p>${fieldValue(bean:venueInstance, field:'description')}</p>
                    <p>${fieldValue(bean:venueInstance, field:'facilities')}</p>
                    <p>${fieldValue(bean:venueInstance, field:'access')}</p>
%{--
                    <div class="contactDetails">
                        <ul>
                            <li><a href="#tabs-${i} .contactDetails .addresses">Addresses</a></li>
                            <li><a href="#tabs-${i} .contactDetails .emails">Email Addresses</a></li>
                            <li><a href="#tabs-${i} .contactDetails .telephoneNumbers">Telephone Numbers</a></li>
                        </ul>
                        <div class="addresses">
                        </div>
                        <div class="emails">
                        </div>
                        <div class="telephoneNumbers">
                        </div>
                    </div>
                    <div class="findUsDetails">
                        <ul>
                            <li><a href="#tabs-${i} .contactDetails .map">Map</a></li>
                        </ul>
                        <div class="map">
                        </div>
                    </div>
                    <div class="roomDetails">
                        <ul>
                            <g:each in="${venueInstance.rooms}" status="j" var="roomInstance">
                            <li><a href="#tabs-${i} .roomDetails .room${j}">${fieldValue(bean:roomInstance, field:'name')}</a></li>
                            </g:each>
                        </ul>
                        <g:each in="${venueInstance.rooms}" status="j" var="roomInstance">
                        <div class="room${j}">
                            <g:if test="${roomInstance.image}">
                            <img src="${createLink(controller: 'image', action: 'src', id: roomInstance.image.id)}" title="${roomInstance.image.name}" alt="${roomInstance.image.name}"/>
                            </g:if>
                            <p>${fieldValue(bean:roomInstance, field:'description')}</p>
                        </div>
                        </g:each>
                    </div>
                    <g:link action="delete">Delete</g:link> <g:link action="edit">Edit</g:link>
--}%
                </div><!-- End of Venue Contents Tab -->
                </g:each>
            </div>
            </g:if>
        </div>
    </body>
</html>