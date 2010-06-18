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
  </head>
  <body>
    <div class="grid_16">
        <g:each var="venue" in="${venues}">
            <h2>${venue.name}</h2>
            ${venue?.content?.encodeAsTextile()}
             <g:if test="${venue?.image}">
                <g:if test="${venue?.image?.mimeType.endsWith('png')}">
                  <img src="${createLink(controller: 'image', action: 'src', id: venue.image.id)}" title="${venue.image.name}" alt="${venue.image.name}" class="pngImg"/>
                </g:if>
                <g:else>
                  <img src="${createLink(controller: 'image', action: 'src', id: venue.image.id)}" title="${venue.image.name}" alt="${venue.image.name}"/>
                </g:else>
              </g:if>            
            <div class="addresses">
                <h3><g:message code="contact.addresses"/></h3>
                <ul>
                    <g:each var="address" in="${venue.addresses}">
                        <li><em>${address.name}</em><br/>
                            ${address.placeName}<br/>
                            ${address.streetNumber} ${address.line1}<br/>
                            <g:each var="v" in="${[address.line2, address.postTown,address.county,address.country,address.postCode]}">
                                <g:if test="${v}">
                                ${v}<br/>
                                </g:if>      
                            </g:each>                      
                        </li>
                    </g:each>
                </ul>
            </div>
            <div class="telephoneNumbers">
                <h3><g:message code="contact.telephone.numbers"/></h3>
                <ul>
                    <g:each var="telephone" in="${venue.telephoneNumbers}">
                        <li class="${telephone.type}">
                            <em>${telephone.name}</em> ${telephone.number}
                        </li>
                    </g:each>
                </ul>                
            </div> 
            <div class="emailAddress">
                <h3><g:message code="contact.emails"/></h3>
                <ul>
                    <g:each var="email" in="${venue.emails}">
                        <li class="${email.type}">
                            <em>${email.name}</em> ${email.address}
                        </li>
                    </g:each>
                </ul>                
            </div>                       
        </g:each>
    </div>

    <div class="clear"></div>

  </body>
</html>
