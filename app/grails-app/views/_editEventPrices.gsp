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
  Template for editing Event prices.

  User: Leanne Northrop
  Date: Jun 30, 2010, 23:04
--%>

<%@ page import="org.joda.time.TimeOfDay;org.samye.dzong.london.media.Image;org.samye.dzong.london.venue.Venue;org.samye.dzong.london.community.Teacher;org.samye.dzong.london.ShiroUser" contentType="text/html;charset=UTF-8" %>
<fieldset>
  <legend>Prices</legend>  
  <g:set var="pricelabels" value="${[F: 'full',S: 'subsidize', M: 'mature',O:'other']}"/>
  <g:each var="price" in="${event.prices}" status="i">
    <p>
      <label for="priceList[${i}].price"><g:message code="event.price.${pricelabels[price.category]}"/></label>
      <g:set var="pvalue"><g:formatNumber number="${price.price}" format="#,##0.00;(#,##0.00)" minIntegerDigits="1" maxFractionDigits="2" roundingMode="HALF_DOWN"/></g:set>
      <g:textField name="priceList[${i}].price" value="${pvalue}" class="required ui-corner-all ${hasErrors(bean:event,field:'price.${pricelabels[i]}','errors')}" minlength="4" decimal="true"/>
      <g:hiddenField name="priceList[${i}].id" value='${price.id}'/>
      <g:hiddenField name="priceList[${i}]._deleted" value='${false}'/>
      <g:hiddenField name="priceList[${i}].category" value='${price.category}'/>
    </p>
  </g:each>
</fieldset>