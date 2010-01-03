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
            		$("#tabs").tabs();
            	});
        </g:javascript>
    </head>
    <body>
        <div class="jquery-ui content">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <g:link action="create"><span class="ui-icon ui-icon-plus"/>&nbsp;Add Venue</g:link>
            <g:if test="${venueInstanceList}">
            <div id="tabs">
            	<ul>
            	    <g:each in="${venueInstanceList}" status="i" var="venueInstance">
            		<li><a href="#tabs-${i}">${fieldValue(bean:venueInstance, field:'name')}</a></li>
            		</g:each>
            	</ul>
            	<g:each in="${venueInstanceList}" status="i" var="venueInstance">
            	<div id="tabs-${i}">
            		<p>${fieldValue(bean:venueInstance, field:'description')}</p>
            	</div>
            	</g:each>
            </div>
            </g:if>
        </div>
    </body>
</html>
