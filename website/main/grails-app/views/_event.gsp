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
    <div class="grid_4 ${event?.category}" style="overflow-x: hidden;">
      <g:set var="startdate"><joda:format style="M-" date="${rule?.startDate}"/></g:set>
      <g:set var="enddate"><joda:format style="M-" date="${rule?.endDate}"/></g:set>
      <g:set var="days" value="${rule?.getDays().sort()}"/>

      <g:if test="${!rule?.isRule}">
        <h3>${startdate}</h3>
      </g:if>
      <g:else>
        <g:if test="${rule?.isDaily()}">
          <h3><g:message code="day.interval.${rule?.interval}"/></h3>
        </g:if>
        <g:elseif test="${rule?.isWeekly()}">
          <h3>
            <g:each var="day" in="${days}" status="index">
              <g:message code="${day}"/><g:if test="${index < rule?.getDays().size()-1}">,&nbsp;</g:if>
            </g:each>
          </h3>
          <h4><g:message code="week.interval.${rule?.interval}"/></h4>
        </g:elseif>
        <g:elseif test="${rule?.isMonthly()}">
          <h3>
            <g:each var="modifiers" in="${rule?.getModifiers()}" status="index">
                <g:message code="month.${modifiers[0]}"/>&nbsp;<g:message code="${modifiers[1]}"/>
            </g:each>
          </h3>
          <h4><g:message code="month.interval.${rule?.interval}"/></h4>
        </g:elseif>

        <g:if test="${rule?.isBounded()}">
          <h4><g:message code="from.until" args="${[startdate,enddate]}"/></h4>
        </g:if>
      </g:else>

      <h6><joda:format pattern="h:mm" value="${rule?.startTime?.toDateTimeToday()}"/> - <joda:format pattern="h:mm a" value="${rule?.endTime?.toDateTimeToday()}"/>  (${fieldValue(bean: rule, field: "duration")})</h6>

      <g:if test="${event?.leader}">
        <h5>${event?.leader}</h5>
      </g:if>

      <g:if test="${event?.prices}">
        <ul>
          <g:set var="pricelabels" value="${[F: 'full',S: 'subsidize', M: 'mature',O:'other']}"/>
          <g:set var="currencySymbol" value="${event?.prices[0].currency.getSymbol() == 'GBP' ? '£' : event?.prices[0].currency.getSymbol()}"/>
          <g:set var="emptyPrice" value="${(currencySymbol + '0.00')}"/>
          <g:each var="price" in="${event.prices}" status="i">
            <g:set var="pvalue"><g:formatNumber number="${price.price}" format="${currencySymbol}#,##0.00;(#,##0.00)" minIntegerDigits="1" maxFractionDigits="2" roundingMode="HALF_DOWN"/></g:set>
            <g:if test="${pvalue != emptyPrice}">
              <li><g:message code="event.price.${pricelabels[price.category]}"/> ${pvalue}</li>
            </g:if>
          </g:each>
        </ul>
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
    <g:each in="${articles}" status="i" var="articleInstance">
      <li class="article"><g:link action="view" id="${articleInstance.id}">${articleInstance.title}</g:link></li>
    </g:each>
  </ul>
</div>
<div class="clear"></div>



