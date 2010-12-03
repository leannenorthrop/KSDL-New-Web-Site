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
  Template for display an event and related items.

  User: Leanne Northrop
  Date: Feb 12, 2010,9:53:27 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<g:set var="rule" value="${event?.dates[0]}"/>
<div class="grid_12">
  <div class="grid event">
    <div class="grid_4 ${event?.category}" >
      <g:set var="startdate"><g:formatDate format="dd-MM-yyyy" date="${rule?.startDate}"/></g:set>
      <g:set var="enddate"><g:formatDate format="dd-MM-yyyy" date="${rule?.endDate}"/></g:set>
      <g:set var="days" value="${rule?.getDays().sort()}"/>

      <g:if test="${event?.leader}">
        <h3>
            <g:if test="${event.leader.title != 'U'}">
            with
            </g:if>
            <g:link controller="aboutUs" action="teacher" id="${event.leader.id}">${event.leader}</g:link>
        </h3>
      </g:if>

      <g:if test="${!rule?.isRule}">
            <g:if test="${rule?.isSeveral()}">
            <h4>
                <g:each var="date" status="index" in="${event.dates}">
                    <g:if test="${index > 0}">
                        <g:if test="${event.dates.size() == 2}">&amp;
                        </g:if>
                        <g:else>,</g:else>
                    </g:if>
                    ${date.startDate.format('d MMMM yyyy')}
                </g:each>
            </h4>
            </g:if>
            <g:else>
            <h4>${startdate}</h4>            
            </g:else>
      </g:if>
      <g:else>
        <g:if test="${rule?.isDaily()}">
          <h4><g:message code="day.interval.${rule?.interval}"/></h4>
        </g:if>
        <g:elseif test="${rule?.isWeekly()}">
          <h4>
            <g:each var="day" in="${days}" status="index">
              <g:message code="${day}"/><g:if test="${index < rule?.getDays().size()-1}">,&nbsp;</g:if>
            </g:each>
          </h4>
          <h5><g:message code="week.interval.${rule?.interval}"/></h5>
        </g:elseif>
        <g:elseif test="${rule?.isMonthly()}">
          <h4>
            <g:each var="modifiers" in="${rule?.getModifiers()}" status="index">
                <g:message code="month.${modifiers[0]}"/>&nbsp;<g:message code="${modifiers[1]}"/>
            </g:each>
          </h4>
          <h5><g:message code="month.interval.${rule?.interval}"/></h5>
        </g:elseif>

        <g:if test="${rule?.isBounded()}">
          <h4><g:message code="from.until" args="${[startdate,enddate]}"/></h4>
        </g:if>
      </g:else>

      <h5>
      <joda:format pattern="h:mm" value="${rule?.startTime?.toDateTimeToday()}"/> - <joda:format pattern="h:mm a" value="${rule?.endTime?.toDateTimeToday()}"/> 
      <g:if test="${rule.duration}">
      (${fieldValue(bean: rule, field: "duration")})
      </g:if>
      </h5>

      <g:if test="${event?.prices}">
        <ul>
          <g:each var="price" in="${event.prices}" status="i">
            <g:if test="${price != 0.0d}">
              <li>${price}</li>
            </g:if>
          </g:each>
        </ul>
      </g:if>

      <g:if test="event?.organizer">
        <h6><g:link controller="event" action="query" id="${event.id}"><g:message code="event.query"/></g:link></h6>
      </g:if>
    </div>

    <div class="grid_12 body ${event?.category}">
      <g:if test="${event?.image && event?.image?.mimeType.endsWith('png')}">
        <img src="${createLink(controller: 'image', action: 'src', id: event.image.id)}" title="${event.image.name}" alt="${event.image.name}" class="pngImg"/>
      </g:if>
      <g:elseif test="${event?.image}">
        <img src="${createLink(controller: 'image', action: 'src', id: event.image.id)}" title="${event.image.name}" alt="${event.image.name}"/>
      </g:elseif>
      ${event.content.encodeAsTextile()}

      <p class="meta">
        <g:message code="article.labels"/> ${articleInstance?.tags?.join(", ")}
        <g:if test="${event.datePublished}">
          Posted: <span class="date"><g:formatDate format="dd MMMM, yyyy" date="${event.datePublished}"/></span>
        </g:if>
        <g:if test="${event.displayAuthor}">
          by <a>${event.author.username}</a>
        </g:if>
      </p>
    </div>
  </div>
</div>

<div class="grid_4">
  <h3><g:message code="similar"/></h3>
  <ul>
    <g:each in="${articles}" status="i" var="eventInstance">
      <g:set var="eventViewController"><g:message code="publish.category.controller.${eventInstance?.category}"/></g:set>
      <li class="event"><g:link controller="${eventViewController}" action="event" id="${eventInstance.id}">${eventInstance.title}</g:link></li>
    </g:each>
  </ul>
</div>
<div class="clear"></div>



