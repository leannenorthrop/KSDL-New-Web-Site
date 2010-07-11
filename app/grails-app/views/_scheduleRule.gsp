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
  Template for editing schedule rule information.

  User: Leanne Northrop
  Date: Jul 2, 2010, 17:26
--%>

<%@ page import="org.joda.time.TimeOfDay;" contentType="text/html;charset=UTF-8" %>

<div id="${prop}RuleType">
    <ul>
        <li><a href="#${prop}Daily"><g:message code="a" default="Daily"/></a></li>
        <li><a href="#${prop}Weekly"><g:message code="b" default="Weekly"/></a></li>
        <li><a href="#${prop}Monthly"><g:message code="c" default="Monthly"/></a></li>
    </ul>
    <div id="${prop}Daily" style="height:13em;width:90%;">
        <g:select name="ruledailyinterval" from="${1..6}" valueMessagePrefix="day.interval" class="ui-corner-all" value="${rule?.interval}"/>            
    </div>
    <div id="${prop}Weekly" style="height:13em;width:90%;">
        <g:select name="ruleweeklyinterval" from="${1..4}" valueMessagePrefix="week.interval" class="ui-corner-all" value="${rule?.interval}"/>
        <g:each var="day" in="${['MO','TU','WE','TH','FR','SA','SU']}">
            <g:if test="${rule?.modifier.contains(day)}">
            <input type="checkbox" name="ruleweekly${day}" class="ruleweekly${day}" checked="yes"/><g:message code="${day}"/>            
            </g:if>
            <g:else>
            <input type="checkbox" name="ruleweekly${day}" class="ruleweekly${day}"/><g:message code="${day}"/>
            </g:else>
        </g:each>            
    </div>
    <div id="${prop}Monthly" style="height:13em;width:90%;">
        <g:select name="rulemonthlyinterval" from="${1..12}" valueMessagePrefix="month.interval" class="ui-corner-all" value="${rule?.interval}"/>
        <g:each in="${['one','two']}" var="instance">
            <p>
                <g:select name="rulemonthly${instance}interval" class="rulemonthly${instance}interval ui-corner-all" from="${1..5}" valueMessagePrefix="month"/>
                <g:each in="${['MO','TU','WE','TH','FR','SA','SU']}">
                    <input type="checkbox" name="rulemonthly${instance}${it}" class="rulemonthly${instance}${it}" value="rulemonthly${instance}${it}"/><g:message code="${it}"/>
                </g:each>
            </p>
        </g:each>            
    </div>                
</div> 
<g:if test="${rule?.isDaily()}">
    <g:set var="tabIndex" value="0"/>
</g:if>
<g:elseif test="${rule?.isWeekly()}">
    <g:set var="tabIndex" value="1"/>   
</g:elseif>
<g:elseif test="${rule?.isMonthly()}">
    <g:set var="tabIndex" value="2"/> 
    <g:set var="matcher" value="${rule?.modifier =~ /\d[+-] [A-Z][A-Z]/}"/>     
    <g:set var="firstWeekInterval"/>
    <g:set var="oneDays" value="${[]}"/>    
    <g:set var="twoDays" value="${[]}"/>    
    <g:set var="oneInterval"/>
    <g:set var="twoInterval"/>
    <g:each in="${matcher}" var="weekModifiers" status="i">
        <g:set var="weekInterval" value="${weekModifiers.split()[0]}"/>            
        <g:set var="weekDay" value="${weekModifiers.split()[1]}"/>     
        <g:if test="${i == 0}">
            <g:set var="firstWeekInterval" value="${weekModifiers.split()[0]}"/>                                                                
        </g:if>
        <g:if test="${firstWeekInterval == weekInterval}">
            <g:if test="${weekInterval == '1-'}">
                <g:set var="oneInterval" value="5"/>            
            </g:if>
            <g:else>
                <g:set var="oneInterval" value="${Integer.valueOf(weekInterval[0..0])}"/>
            </g:else>
            <g:set var="oneDays" value="${oneDays << weekDay}"/>
        </g:if>        
        <g:else>
            <g:if test="${weekInterval == '1-'}">
                <g:set var="twoInterval" value="5"/>            
            </g:if>
            <g:else>
                <g:set var="twoInterval" value="${Integer.valueOf(weekInterval[0..0])}"/>
            </g:else>       
            <g:set var="twoDays" value="${twoDays << weekDay}"/>
        </g:else>
    </g:each>    
    <g:set var="oneids"><g:each in="${oneDays}">"rulemonthlyone${it}",</g:each></g:set>
    <g:set var="twoids"><g:each in="${twoDays}">"rulemonthlytwo${it}",</g:each></g:set>        
    <g:set var="js">
        $("#${prop}Monthly input").val([${oneids}${twoids}]);
        $("#rulemonthlyoneinterval").val(${oneInterval});
        $("#rulemonthlytwointerval").val(${twoInterval});        
    </g:set>
</g:elseif>
<g:javascript>
    $("#${prop}RuleType").tabs({
        selected: ${tabIndex},
        fx: { opacity: 'toggle' },
        select: function(event, ui) {
        }        
    });   
    ${js}     
</g:javascript>