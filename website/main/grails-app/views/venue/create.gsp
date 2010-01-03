<%@ page import="org.samye.dzong.london.venue.Room" %>
<%@ page import="org.samye.dzong.london.venue.Venue" %>
<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title>Create New Venue</title>   
        <link rel="stylesheet" href="${resource(dir:'css/redmond',file:'jquery-ui-1.7.2.custom.css')}" media="screen, projection" />
        <g:javascript library="jquery"/>
        <g:javascript src="jquery/jquery-ui-1.7.2.custom.min.js"/>        
        <g:javascript>
            var roomsCount = ${venueInstance.rooms.size()};
            
            function removeRoom(elem) {
                
            };
            
            function addRoom() {
                var namePrefix = "rooms[" + rooms + "]";
                var liElem = $("#roomClone").clone(true);
                liElem.attr({id:"room" + roomsCount});
                liElem.find('.room .name input').attr(name: namePrefix + ".name");
                liElem.find('.room .image input').attr(name: namePrefix + ".imageName");                
                liElem.find('.room .description textarea').attr(name: namePrefix + ".description");
                liElem.find('.room .makeRoomPublic input').attr(name: namePrefix + ".makePublic");                
                $("#rooms").append(liElem);
                roomsCount += 1;                           
            }
            
        	$(function() {
        		$("#details").tabs();
        		$('#addRoom').click(function() {
        		    addRoom();
        		});
        	});
        </g:javascript>          
    </head>
    <body>
        <div class="jquery-ui content">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${venueInstance}">
            <div class="errors">
                <g:renderErrors bean="${venueInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <input type="hidden" name="publishState" value="Unpublished" />
                <input type="hidden" name="deleted" value="false" />            
                <fieldset>
                    <label for="name">Name</label>
                    <input type="text" id="name" name="name" value="${fieldValue(bean:venueInstance,field:'name')}"/>
                </fieldset>                    
                <fieldset>
                    <label for="image">Image</label>
                    <g:select optionKey="id" from="${org.samye.dzong.london.media.Image.list()}" name="image.id" value="${venueInstance?.imageName}" ></g:select>
                </fieldset>    
                <fieldset>
                    <label for="description">Description</label>
                    <g:textArea name="description" value="${fieldValue(bean:venueInstance,field:'description')}" rows="5" cols="40"/>
                </fieldset>
                <fieldset>
                    <label for="facilities">Facilities</label>
                    <g:textArea name="facilities" value="${fieldValue(bean:venueInstance,field:'facilities')}" rows="5" cols="40"/>
                </fieldset>    
                <fieldset>
                    <label for="access">Access</label>
                    <g:textArea name="access" value="${fieldValue(bean:venueInstance,field:'access')}" rows="5" cols="40"/>
                </fieldset>             
                <div id="details">
                    <ul>
                		<li><a href="#addresses">Addresses</a></li>
                		<li><a href="#telephoneNumbers">Telephone Numbers</a></li>
                		<li><a href="#emails">Emails</a></li>
                		<li><a href="#visitingUs">Visiting Us</a></li>                		                		                		
                		<li><a href="#rooms">Rooms</a></li>                		
                	</ul>
                	<div id="addresses" style="min-height: 15em">
                		
                	</div>
                	<div id="telephoneNumbers" style="min-height: 15em">
                		
                	</div>
                	<div id="emails" style="min-height: 15em">
                		
                	</div>
                	<div id="visitingUs" style="min-height: 15em">
                		
                	</div>
                	<div id="rooms" style="height: 15em">
                	    <a href="#" id="addRoom"><span class="ui-icon ui-icon-plus"/>Add Room</a>
                		<ul id="rooms">
                		    <g:each in="${venueInstance.rooms}" status="j" var="roomInstance">
                    		    <g:set var="namePrefix" value="${'rooms[' + j + ']'}" />
                		        <li>
                                    <fieldset>
                                        <label for="${namePrefix}.name">Name</label>
                                        <input type="text" name="${namePrefix}.name" value="${fieldValue(bean:roomInstance,field:'name')}"/>
                                    </fieldset>                    
                                    <fieldset>
                                        <label for="${namePrefix}.imageName">Image</label>
                                        <g:select optionKey="id" from="${org.samye.dzong.london.media.Image.list()}" name="${namePrefix}.imageName" value="${venueInstance?.imageName}" ></g:select>
                                    </fieldset>    
                                    <fieldset>
                                        <label for="${namePrefix}.description">Description</label>
                                        <g:textArea name="${namePrefix}.description" value="${fieldValue(bean:roomInstance,field:'description')}" rows="5" cols="40"/>
                                    </fieldset>
                                    <g:checkBox name="${namePrefix}.makePublic" value="${false}" />               		          
                		        </li>
                		    </g:each>
            		    </ul>
                	</div>                	
                </div>                                                                           
                <input class="save" type="submit" value="Create" />
            </g:form>
        </div>
        <div style="visibility:hidden;display:none;">
            <li id="roomClone" class="room">
                <fieldset class="name">
                    <label>Name</label>
                    <input type="text"value=""/>
                </fieldset>                    
                <fieldset class="image">
                    <label>Image</label>
                    <g:select optionKey="id" from="${org.samye.dzong.london.media.Image.list()}"></g:select>
                </fieldset>    
                <fieldset class="description">
                    <label>Description</label>
                    <textArea rows="5" cols="40">&nbsp;</textarea>
                </fieldset>
                <g:checkBox class="makeRoomPublic" value="${false}" />               		          
                <button onClick="removeRoom(this);return false;">Add Room</button>
	        </li>            
        </div>
    </body>
</html>
