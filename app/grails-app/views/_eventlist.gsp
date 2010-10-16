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
  Template for displaying a list of events.
  User: Leanne Northrop
  Date: Feb 12, 2010, 7:34:02 PM
--%>
<%@ page import="org.samye.dzong.london.community.Teacher; org.samye.dzong.london.events.Event" contentType="text/html;charset=UTF-8" %>

<div class="box border2">
  <h3><g:message code="${heading}" default=""/></h3>
  <ul class="block events">
    <g:if test="${events}">

      <g:each in="${events}" status="i" var="event">
        <g:set var="rule" value="${event?.dates[0]}"/>
        <g:set var="placementClass" value="${i == 0 ? 'first' : (i == events.size ? 'last' :'')}"/>
        <g:set var="eventViewController"><g:message code="publish.category.controller.${event?.category}"/></g:set>

        <li class="event ${placementClass} ${event?.category}">

          <g:set var="eventHeading" value="${event.title}"/>
          <h4>
            <g:if test="${event.content}">
              <g:link controller="${eventViewController}" action="event" id="${event.id}">${eventHeading}</g:link>
            </g:if>
            <g:else>
              ${eventHeading}
            </g:else>
          </h4>

          <g:set var="startdate"><g:formatDate date="${rule?.startDate}" format="d MMMM yyyy"/></g:set>
          <g:set var="enddate"><g:formatDate date="${rule?.endDate}" format="d MMMM yyyy"/></g:set>
          <g:set var="days" value="${rule?.getDays().sort()}"/>

          <g:if test="${!rule?.isRule}">
            <g:if test="${rule?.isSeveral()}">
            <h5>
                <g:each var="d" in="${event.dates}">
                    <g:formatDate date="${d?.startDate}" format="d MMMM yyyy"/>,
                </g:each>
            </h5>
            </g:if>
            <g:else>
            <h6>${startdate}</h6>            
            </g:else>
          </g:if>
          <g:else>
            <g:if test="${rule?.isDaily()}">
              <h5><g:message code="day.interval.${rule?.interval}"/></h5>
            </g:if>
            <g:elseif test="${rule?.isWeekly()}">
              <h5>
                <g:each var="day" in="${days}" status="index">
                  <g:message code="${day}"/><g:if test="${index < rule?.getDays().size()-1}">,&nbsp;</g:if>
                </g:each>
                <g:message code="week.interval.${rule?.interval}"/></h5>
            </g:elseif>
            <g:elseif test="${rule?.isMonthly()}">
              <h5>
                <g:each var="modifiers" in="${rule?.getModifiers()}" status="index">
                  <g:message code="month.${modifiers[0]}"/>&nbsp;<g:message code="${modifiers[1]}"/>
                </g:each>
                <g:message code="month.interval.${rule?.interval}"/>
              </h5>
            </g:elseif>

            <g:if test="${rule?.isBounded()}">
              <h5><g:message code="from.until" args="${[startdate,enddate]}"/></h5>
            </g:if>
          </g:else>

          <g:if test="${event.displayAuthor || event.displayDate}">
            <p class="meta">
              <joda:format pattern="h:mm" value="${rule?.startTime?.toDateTimeToday()}"/> - <joda:format pattern="h:mm a" value="${rule?.endTime?.toDateTimeToday()}"/>  (${fieldValue(bean: rule, field: "duration")})

              <g:if test="${event?.leader}">
                with ${event?.leader}
              </g:if>
            </p>
          </g:if>

          <g:if test="${event.content}">
              <g:link controller="${eventViewController}" action="event" id="${event.id}">
                  <g:if test="${event.image}">
                      <img src="${createLink(controller: 'image', action: 'thumbnail', id: event.image.id)}" title="${event.image.name}" alt="${event.image.name}"/>
                  </g:if>
                  <g:else>
                      <img class="defaultImg">&nbsp;</img>
                  </g:else>
              </g:link>
          </g:if>
          <g:else>
              <g:if test="${event.image}">
                  <a href="#">
                      <img src="${createLink(controller: 'image', action: 'thumbnail', id: event.image.id)}" title="${event.image.name}" alt="${event.image.name}"/>
                  </a>
              </g:if>
              <g:else>
                  <a href="#">
                      <img class="defaultImg">&nbsp;</img>
                  </a>
              </g:else>            
          </g:else>          
          <p>
            ${event?.summary?.encodeAsTextile()}
          </p>
          <p>
            <g:if test="${event.content}">
              <g:link controller="${eventViewController}" action="event" id="${event.id}"><g:message code='content.more'/></g:link>
            </g:if>
          </p>
        </li>
      </g:each>

    </g:if>
  </ul>
  <g:if test="${total > events.size()}">
    <g:link action="events"><em><g:message code="events.more"/></em></g:link>
  </g:if>
</div>
