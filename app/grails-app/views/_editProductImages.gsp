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
  Date: 9th July, 2010, 19:54
--%>

<%@ page import="org.samye.dzong.london.media.Image;org.samye.dzong.london.shop.Product" contentType="text/html;charset=UTF-8" %>

<div id="productImageDiv"> 
    <input type="file" name="image" />
    <input type="button" value="+"/>
    <input type="submit" value="add" style="display:none;visibility:hidden">
</div>
<h3>Click image to delete</h3>
<div id="images" style="min-height:75px;position:relative;margin-top:1em">
   <g:each var="image" in="${product.images}" status="count">
   <g:if test="${image?.id}">
    <a class="existingImg" href="#">
        <img src="${createLink(controller:'image',action:'thumbnail',id:image?.id)}"></img>
        <input type="hidden" name="imageList[${count}].id" value="${image?.id}"/>
        <input type="hidden" name="imageList[${count}]._deleted" value="false"/>        
    </a>         
    </g:if>
   </g:each>
</div>
<g:set var="postURL" value="${createLink(controller:'image', action:'createProductImage')}"/>
<g:javascript>
    $("#images a.existingImg").click(function() {
        var parent = $(this).parent();
        var d = $(this).children().filter("[name$='_deleted']");
        d.val('true');
        d.appendTo(parent);
       $(this).remove(); 
    });

    $("form").parent().append('<form id="productImage" action="${postURL}" method="post" style="display:none;visibility:hidden;"/>');

    var options = { 
        dataType:      'xml',
        success:       showResponse  // post-submit callback 
 
        // other available options: 
        //target:        '#output1',   // target element(s) to be updated with server response         
        //beforeSubmit:  showRequest,  // pre-submit callback         
        //url:       url         // override for form's 'action' attribute 
        //type:      type        // 'get' or 'post', override for form's 'method' attribute 
        //dataType:  null        // 'xml', 'script', or 'json' (expected server response type) 
        //clearForm: true        // clear all form fields after successful submit 
        //resetForm: true        // reset the form after successful submit 
 
        // $.ajax options can be used here too, for example: 
        //timeout:   3000 
    };
    
    $("#productImageDiv input:button").click(function() {        
       $("#productImage").children().remove();
       // Clone the "real" input element
       var real = $("#productImageDiv input:file");
       var cloned = real.clone(true);
       // Put the cloned element directly after the real element
       // (the cloned element will take the real input element's place in your UI
       // after you move the real element in the next step)
       real.hide();
       cloned.insertAfter(real);   
       // Move the real element to the hidden form - you can then submit it
       real.appendTo("#productImage");     
       $('#productImage').ajaxSubmit(options); 
    });

    // post-submit callback 
    function showResponse(responseXML)  { 
        var xml = $('product', responseXML);
        var id = xml.find('image').attr('id');
        if (id > -1){
            var name = xml.find('image').attr('name');        
            var count = $("#images input:hidden").size();
            $("#images").append('<a class="newImg" href="#"><img src="${createLink(controller:'image',action:'thumbnail')}/' + id + '" style="width:75px;height:75px;"/></a>').hide().fadeIn();        
            $("#images").append('<input type="hidden" name="imageList[' + count + '].id" value="' + id + '"/>');
            $("#images a.newImg").click(function() {
               $(this).remove(); 
            });
        }
        return false;
    }    
</g:javascript>