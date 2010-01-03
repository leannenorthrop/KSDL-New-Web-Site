<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page import="org.samye.dzong.london.venue.Room" %>
<%@ page import="org.samye.dzong.london.venue.Venue" %>
<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title>Manage Room</title>
        <link rel="stylesheet" href="${resource(dir:'css/redmond',file:'jquery-ui-1.7.2.custom.css')}" media="screen, projection" />
        <g:javascript library="jquery"/>
        <g:javascript src="jquery/jquery-ui-1.7.2.custom.min.js"/>        
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
            	</div><!-- End of Venue Contents Tab -->
            	</g:each>
            </div>
            </g:if>
        </div>
    </body>
</html>
