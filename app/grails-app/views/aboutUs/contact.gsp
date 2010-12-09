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
Contact Us template for About Us
User: Leanne Northrop
Date: Jun 18, 2010, 6:38:08 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title><g:message code="footer.contact.us"/></title>
        <meta name="layout" content="main">
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
        <script src="http://maps.google.com/maps/api/js?sensor=true"></script>        
    </head>
    <body>
        <div class="grid_4">
            <g:render template="/aboutUsNav"/>
        </div>
          
        <div class="grid_12">         
            <div class="container_16">
                <g:each var="venue" in="${venues}">
                    <h2>${venue.name}</h2>
                    <div class="grid_8">
                        <ul class="contacts">
                            <g:each var="address" in="${venue.addresses}">
                                <li class="address"><em>${address.name}</em>
                                        ${address.placeName},&nbsp;
                                        ${address.streetNumber} ${address.line1},
                                    <g:each var="v" in="${[address.line2, address.postTown,address.county,address.country,address.postCode]}">
                                        <g:if test="${v}">${v}&nbsp;</g:if>
                                    </g:each>
                                </li>
                            </g:each>
                            <g:each var="telephone" in="${venue.telephoneNumbers}">
                                <li class="telephone ${telephone.type}"><em>${telephone.name}</em> ${telephone.number}</li>
                            </g:each>   
                            <g:each var="email" in="${venue.emails}">
                                <li class="email ${email.type}"><em>${email.name}</em> ${email.address}</li>
                            </g:each>                                             
                        </ul>
                    </div>
                    <div class="grid_8">   
                        <g:if test="${venue?.image}">
                            <g:if test="${venue?.image?.mimeType.endsWith('png')}">
                                <img src="${createLink(controller: 'image', action: 'src', id: venue.image.id)}" title="${venue.image.name}" alt="${venue.image.name}" class="pngImg"/>
                            </g:if>
                            <g:else>
                                <img src="${createLink(controller: 'image', action: 'src', id: venue.image.id)}" title="${venue.image.name}" alt="${venue.image.name}"/>
                            </g:else>
                        </g:if>
                    </div>
                    <div class="clear"></div>
                </g:each>
            </div>        
        </div>
    </body>    
</html>
