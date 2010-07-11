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
  Template for editing Product shipping.

  User: Leanne Northrop
  Date: 9th July, 2010, 17:06
--%>

<%@ page import="org.samye.dzong.london.media.Image;org.samye.dzong.london.shop.Product" contentType="text/html;charset=UTF-8" %>

<p>
    <g:radioGroup name="isdownloadable" 
                  values="['mail']" 
                  labels="['product.mailit.title','product.downloadable.title']" 
                  value="${product.isdownloadable ? 'download' : 'mail'}" >
                  <g:message code="${it.label}" />: ${it.radio}&nbsp;
    </g:radioGroup>
</p>
<div id="mail" style="display:none;height:23em;">
<p>
    <label for="weight">
        <g:message code="product.weight.label"/>
    </label>
    <g:textField name="weight" 
                 value="${product?.weight}" 
                 class="required digits ui-corner-all ${hasErrors(bean:product,field:'weight','errors')}" 
                 min="0"/>    
</p>
<p>
    <label for="height">
        <g:message code="product.height.label"/>
    </label>
    <g:textField name="height" 
                 value="${product?.height}" 
                 class="required digits ui-corner-all ${hasErrors(bean:product,field:'height','errors')}" 
                 min="0"/>    
</p>
<p>
    <label for="width">
        <g:message code="product.width.label"/>
    </label>
    <g:textField name="width" 
                 value="${product?.width}" 
                 class="required digits ui-corner-all ${hasErrors(bean:product,field:'width','errors')}" 
                 min="0"/>    
</p>
<p>
    <label for="depth">
        <g:message code="product.depth.label"/>
    </label>
    <g:textField name="depth" 
                 value="${product?.depth}" 
                 class="required digits ui-corner-all ${hasErrors(bean:product,field:'depth','errors')}" 
                 min="0"/>    
</p>
</div>
<div id="download" style="display:none;height:23em;">
download
</div>
<g:javascript>
$("input[name$=isdownloadable]").change(function() {
    $("input[name$=isdownloadable]").each(function(index) {
        var id = $(this).val();
        $("#" + id).hide();
    });
    var id2 = $(this).val();
    $("#" + id2).show();            
});

var showDatePanel = $("input[name$=isdownloadable]:checked").val();
$("#" + showDatePanel).show();
</g:javascript>