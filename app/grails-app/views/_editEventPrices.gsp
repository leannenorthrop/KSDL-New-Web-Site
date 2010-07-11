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
  
  <g:hiddenField name="priceList[${i}].category" value='${price.category}'/>
      <g:textField name="priceList[${i}].price" value="${pvalue}" class="required ui-corner-all ${hasErrors(bean:event,field:'price.${pricelabels[i]}','errors')}" minlength="4" decimal="true"/>  
  <g:set var="pvalue"><g:formatNumber number="${price.price}" format="#,##0.00;(#,##0.00)" minIntegerDigits="1" maxFractionDigits="2" roundingMode="HALF_DOWN"/></g:set>        
  <g:set var="priceType"><g:textField name="priceList[${i}].price" value="${pvalue}" class="required ui-corner-all ${hasErrors(bean:event,field:'price.${pricelabels[i]}','errors')}" minlength="4" decimal="true"/> </g:set>  
--%>

<%@ page import="org.joda.time.TimeOfDay;org.samye.dzong.london.media.Image;org.samye.dzong.london.venue.Venue;org.samye.dzong.london.community.Teacher;org.samye.dzong.london.ShiroUser" contentType="text/html;charset=UTF-8" %>
<fieldset>
  <legend>Prices</legend>  
  <g:render template="/clone" model="[propval: 'price',labelCode:'event.price',listName:'priceList',nextId:event.priceList.size(),list:event.priceList]"/>
  
  <div id="additionalPriceHiddenFields" style="display:none;visibility:hidden;">
      <input type="hidden" name="category"> 
  </div>
  <div id="additionalPriceFields" style="display:none;visibility:hidden;">
      <g:select name="pricecategory" from="${['F','S', 'M','O']}" valueMessagePrefix="event.price" class="category"/>
  </div>  
  <g:javascript> 
    var html = $("#additionalPriceHiddenFields").html();
    $("#priceTemplate").append(html);
    var html2 = $("#additionalPriceFields").html(); 
    $("p.priceDetails button.add").before(html2);    
  </g:javascript>   
  
</fieldset>