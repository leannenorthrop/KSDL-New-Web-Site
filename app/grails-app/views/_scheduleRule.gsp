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
        <g:each in="${['MO','TU','WE','TH','FR','SA','SU']}">
            <input type="checkbox" name="ruleweekly${it}" class="ruleweekly${it}"/><g:message code="${it}"/>
        </g:each>            
    </div>
    <div id="${prop}Monthly" style="height:13em;width:90%;">
        <g:select name="rulemonthlyinterval" from="${1..12}" valueMessagePrefix="month.interval" class="ui-corner-all" value="${rule?.interval}"/>
        <g:each in="${['one','two']}" var="instance">
            <p>
                <g:select name="rulemonthly${instance}interval" class="rulemonthly${instance}interval ui-corner-all" from="${1..5}" valueMessagePrefix="month"/>
                <g:each in="${['MO','TU','WE','TH','FR','SA','SU']}">
                    <input type="checkbox" name="rulemonthly${instance}${it}" class="rulemonthly${instance}${it}"/><g:message code="${it}"/>
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
</g:elseif>
<g:javascript>
    $("#${prop}RuleType").tabs({
        selected: ${tabIndex},
        fx: { opacity: 'toggle' },
        select: function(event, ui) {
        }        
    });
    $("#${prop}Daily select[name$=dailyinterval]").val(${firstDate?.interval});
    $("#${prop}Weekly select[name$=weeklyinterval]").val(${firstDate?.interval});    
    $("#${prop}Monthly select[name$=monthlyinterval]").val(${firstDate?.interval});        

    //${firstDate?.modifier}
    //${firstDate?.modifierType}
</g:javascript>