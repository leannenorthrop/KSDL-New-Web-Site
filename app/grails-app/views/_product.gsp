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
  Template for displaying a single product.

  User: Leanne Northrop
  Date: 10th July 2010, 17:58
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<div class="grid product">
    <div class="grid_4" style="overflow-x: hidden;">
        <h4>${product?.title}</h4>
        <h5>${product?.prices[0]}</h5>
        <g:if test="${product?.images.size() > 0}">
            <g:if test="${product?.images[0].mimeType.endsWith('png')}">
                <img src="${createLink(controller: 'image', action: 'src', id: product?.images[0].id)}" title="${product?.title}" alt="${product?.title}" class="pngImg"/>
            </g:if>
            <g:else>
                <img src="${createLink(controller: 'image', action: 'src', id: product?.images[0].id)}" title="${product?.title}" alt="${product?.title}"/>
            </g:else>                
        </g:if>        
    </div>

    <div class="grid_12 body">
        ${product.content.encodeAsTextile()}

        <g:if test="${product?.tags}">
        <p class="meta">
        <g:message code="product.labels"/> ${product?.tags?.join(", ")}
        </p>
        </g:if>
    </div>
</div>  


