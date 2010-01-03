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
            	$(function() {
            		$("#tabs").tabs();
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
                    <fieldset>
                        <label for="name">Name</label>
                        <input type="text" id="name" name="name" value="${fieldValue(bean:venueInstance,field:'name')}"/>
                    </fieldset>                    
                    <fieldset>
                        <label for="image">Image</label>
                        <g:select optionKey="id" from="${org.samye.dzong.london.media.Image.list()}" name="image.id" value="${venueInstance?.image?.name}" ></g:select>
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
                    <input class="save" type="submit" value="Create" />
            </g:form>
        </div>
    </body>
</html>
