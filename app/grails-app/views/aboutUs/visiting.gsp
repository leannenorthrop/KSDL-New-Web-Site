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
  Visiting Us template for About Us
  User: Leanne Northrop
  Date: Jun 18, 2010, 6:38:08 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title><g:message code="footer.visit.us"/></title>
        <meta name="layout" content="main">
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
        <script src="http://maps.google.com/maps/api/js?sensor=true"></script>            
    </head>
    <body>
        <g:each var="venue" in="${venues}">
            <div class="container_16">
                <div class="grid_16">
                    <h3>${venue.name}</h3>
                    ${venue?.content?.encodeAsTextile()} 
                </div>
                <div class="grid_8 image">
                    <g:if test="${venue?.image}">
                        <g:if test="${venue?.image?.mimeType.endsWith('png')}">
                            <img src="${createLink(controller: 'image', action: 'src', id: venue.image.id)}" title="${venue.image.name}" alt="${venue.image.name}" class="pngImg"/>
                        </g:if>
                        <g:else>
                            <img src="${createLink(controller: 'image', action: 'src', id: venue.image.id)}" title="${venue.image.name}" alt="${venue.image.name}"/>
                        </g:else>
                    </g:if>                    
                </div>                
                <div class="grid_8 facilities">
                    <h3><g:message code="venue.facilities.label"/></h3>
                    ${venue?.facilities?.encodeAsTextile()}</div>           
                <div class="clear"></div> 

                <div class="grid_8 access">
                    <h3><g:message code="venue.access.label"/></h3>
                    ${venue?.access?.encodeAsTextile()}
                </div>

                <div class="grid_8 map">
                    <div id="map_canvas_${venue.id}" style="width:100%; height:100%;min-height:350px;min-width:400px;"></div>
                </div>
                <div class="clear"></div> 
                
                <g:javascript type="text/javascript">
                    var latlng = new google.maps.LatLng(${venue.latitude}, ${venue.longtitude});
                    var myOptions = {
                        zoom: 16,
                        center: latlng,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };
                    var map = new google.maps.Map(document.getElementById("map_canvas_${venue.id}"), myOptions);
                    var marker = new google.maps.Marker({
                        position: latlng, 
                        map: map, 
                        title:"${venue.name}"
                    });                
                </g:javascript>                
            </div>
        </g:each>
    </body>
</html>
