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
  Template for editing Products.

  User: Leanne Northrop
  Date: 9th July, 2010, 16:54
--%>

<%@ page import="org.samye.dzong.london.media.Image;org.samye.dzong.london.shop.Product" contentType="text/html;charset=UTF-8" %>

<div id="jserrors" class="ui-widget ui-state-error ui-corner-all" style="display:none;">
    <strong>
      <span class="ui-icon ui-icon-alert" style="display: inline-block"></span><g:message code="alert"></g:message>
    </strong>
    <ol>
      <li><label for="title" class="error">Please enter a title of at least 5 characters.</label></li>
      <li><label for="summary" class="error">Please add a summary of at least 5 characters.</label></li>
      <li><label for="category" class="error">Please select a category.</label></li>
    </ol>
</div>

<fieldset>
  <legend>Details</legend>
  <g:render template="/editProductDetails" model="[product:product]"/>
</fieldset>

<fieldset>
  <legend>Images</legend>
  <g:render template="/editProductImages" model="[product:product]"/>
</fieldset>

<fieldset>
  <legend>Prices</legend>
  <g:render template="/clone" model="[propval: 'price',labelCode:'product.price',listName:'priceList',nextId:product.priceList.size(),list:product.priceList]"/>
  
  <div id="additionalPriceHiddenFields" style="display:none;visibility:hidden;">
      <input type="hidden" name="category"> 
  </div>
  <div id="additionalPriceFields" style="display:none;visibility:hidden;">
      <g:select name="pricecategory" from="${['F','D']}" valueMessagePrefix="product.price" class="category"/>
  </div>  
  <g:javascript> 
    var html = $("#additionalPriceHiddenFields").html();
    $("#priceTemplate").append(html);
    var html2 = $("#additionalPriceFields").html(); 
    $("p.priceDetails button.add").before(html2);    
  </g:javascript>
</fieldset>

<fieldset>
  <legend>Shipping</legend>
  <g:render template="/editProductShipping" model="[product:product]"/>
</fieldset>

<fieldset>
  <legend>Additional Information</legend>
  <g:render template="/clone" model="[propval: 'type',labelCode:'product.meta',listName:'metaList',nextId:product.metaList.size(),list:product.metaList]"/>
  <div id="additionalMetaHiddenFields" style="display:none;visibility:hidden;">
      <input type="hidden" name="value"> 
  </div>
  <div id="additionalMetaFields" style="display:none;visibility:hidden;">
      <label for="value">
          <g:message code="product.meta.value.label"/>
      </label>      
      <g:textField name="value" 
                   class="ui-corner-all value" 
                   minlength="5"
                   style="display:inline;width:10em;"/>      
  </div>  
  <g:javascript> 
    var html = $("#additionalMetaHiddenFields").html();
    $("#typeTemplate").append(html);
    var html2 = $("#additionalMetaFields").html(); 
    $("p.typeDetails button.add").before(html2);     
  </g:javascript>  
</fieldset>

<fieldset>
  <legend>Menu Categories</legend>
  <g:render template="/clone" model="[propval: 'name',labelCode:'product.categories',listName:'menuCategoriesList',nextId:product.menuCategoriesList.size(),list:product.menuCategoriesList]"/>
  <div id="additionalCategoryHiddenFields" style="display:none;visibility:hidden;">
      <input type="hidden" name="level"> 
  </div>
  <div id="additionalCategoryFields" style="display:none;visibility:hidden;">
      <g:select name="level" from="${0..2}" valueMessagePrefix="product.category.level" class="level"/>
  </div>  
  <g:javascript> 
    var html = $("#additionalCategoryHiddenFields").html();
    $("#nameTemplate").append(html);
    var html2 = $("#additionalCategoryFields").html(); 
    $("p.nameDetails button.add").before(html2);     
  </g:javascript>  
</fieldset>

<fieldset>
  <legend>Description</legend>
  <g:render template="/contentWithPreview" model="[previewController: 'manageSite',publishableInstance:product]"/>
</fieldset>

    <g:if test="${showPublication}">
<fieldset>    
    <legend>Publication Details</legend>
    <g:render template="/publishDetails" model="[articleInstance:product]"/>
</fieldset>    
    </g:if>

<jq:jquery>
var container = $("#jserrors");
$("form").validate({
    onfocusout: false,
	errorContainer: container,
	errorLabelContainer: $("ol", container),
	wrapper: 'li',
    submitHandler: function(form) {
        $("#additionalMetaHiddenFields").remove();
        $("#additionalMetaFields").remove();        
        $("#additionalPriceHiddenFields").remove();
        $("#additionalPriceFields").remove();
        $("#priceTemplate").remove();
        $(".priceDetails").remove();  
        $("#menuCategoriesTemplate").remove();
        $(".menuCategoriesDetails").remove();  
        $("#additionalCategoryHiddenFields").remove();
        $("#additionalCategoryFields").remove();        
        $("#metaTemplate").remove();
        $(".metaDetails").remove();                    
        $("[name~=isdownloadable]").remove();
        $("form").append('<input type="hidden" name="isdownloadable" value="off">');
        form.submit();
        return true;
    }		
});  
</jq:jquery>


