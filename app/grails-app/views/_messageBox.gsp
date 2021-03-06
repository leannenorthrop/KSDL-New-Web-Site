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
  Template for showing flash messages & errors
  User: Leanne Northrop
  Date: Feb 18, 2010, 1:13:20 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<noscript>
    <div class="ui-widget ui-state-error ui-corner-all">
        <strong>
            <span class="ui-icon ui-icon-alert" style="display: inline-block"></span>
            <g:message code="alert"/>
        </strong><br/>
        <g:message code="manage.js.error"/>
    </div>
</noscript>  
<g:if test="${flash.message}">
    <g:if test="${flash.isError}">
        <div class="ui-widget ui-state-error ui-corner-all">
            <strong>
                <span class="ui-icon ui-icon-alert" style="display: inline-block"></span>
                <g:message code="alert"/>
            </strong><br/>
            <g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/>
            <g:if test="${flash.bean}">
                <g:renderErrors bean="${flash.bean}" as="list"></g:renderErrors>
            </g:if>
        </div>
    </g:if>
    <g:else>                
        <div class="ui-widget ui-state-error ui-corner-all" style="display: none" id="jserrors">
            <strong>
                <span class="ui-icon ui-icon-alert" style="display: inline-block"></span>
                <g:message code="alert"/>
            </strong><br/>
            <ul>
            </ul>
        </div>
    </g:else>
</g:if>                              
<g:else>
    <div class="ui-widget ui-state-error ui-corner-all" style="display: none" id="jserrors">
        <strong>
            <span class="ui-icon ui-icon-alert" style="display: inline-block"></span>
            <g:message code="alert"/>
        </strong><br/>
        <ul>
        </ul>
    </div>
</g:else>
<div class="ui-widget ui-state-highlight ui-corner-all" id="jsmsgbox" style="display:${flash.message && flash.isError ? 'block' : 'none'}">
    <strong>
        <span class="ui-icon ui-icon-info" style="display: inline-block"></span>
        <g:message code="info"/>
    </strong><br/>
    <g:message code="${flash?.message}" args="${flash?.args}" default="${flash?.default}"/>
</div>    
<div id="waitDialog" title="Please Wait">
	<p>Please wait...</p>
</div>
<g:javascript>
    $('#waitDialog').dialog({ autoOpen: false });
    
    function startWait() {
        $('#waitDialog').open();
    }
    
    function stopWait() {
        $('#waitDialog').close();        
    }
</g:javascript>

