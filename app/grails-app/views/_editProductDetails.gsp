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
  Template for editing Product details.

  User: Leanne Northrop
  Date: 9th July, 2010, 17:06
--%>

<%@ page import="org.samye.dzong.london.media.Image;org.samye.dzong.london.shop.Product" contentType="text/html;charset=UTF-8" %>

<p>
<label for="title">
    <g:message code="product.title.label"/>
</label>
<g:textField name="title" 
             value="${product?.title}" 
             class="required ui-corner-all ${hasErrors(bean:product,field:'title','errors')}" 
             minlength="5"/>
</p>
<p>
<label for="summary">
    <g:message code="product.summary.label"/>
</label>
<g:textArea rows="5" 
            cols="40" 
            name="summary" 
            class="required ui-corner-all ${hasErrors(bean:product,field:'summary','errors')}" 
            value="${fieldValue(bean:product,field:'summary')}" 
            minlength="5"/>
</p>

<p>
<label for="isnew">
    <g:message code="product.isnew.label"/>
</label>
<g:if test="${product?.isnew}">
    <input type="checkbox" name="isnew" checked="true"/>
</g:if>
<g:else>
    <input type="checkbox" name="isnew"/>
</g:else>
<label for="isdiscount">
    <g:message code="product.isDiscount.label"/>
</label>
<g:if test="${product?.isdiscount}">
    <input type="checkbox" name="isdiscount" checked="true"/>
</g:if>
<g:else>
    <input type="checkbox" name="isdiscount"/>
</g:else>
</p>