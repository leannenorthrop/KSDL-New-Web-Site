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
  User: Leanne Northrop
  Date: 10th July, 2010
  Time: 17:26
--%>

<%@ page import="org.samye.dzong.london.shop.Product" contentType="text/html;charset=UTF-8" %>
<div class="box">
  <h3><g:message code="${heading}" default=""/></h3>
  <ul class="block products">
    <g:if test="${products}">

      <g:each in="${products}" status="i" var="product">

        <g:set var="placementClass" value="${i == 0 ? 'first' : (i == products?.size() ? 'last' :'')}"/>
        <li class="${placementClass}">

          <g:set var="productTitle">${product?.title}</g:set>
          <h3>
            <g:if test="${product?.content}">
              <g:link controller="shop" action="display" id="${product.id}">${productTitle}</g:link>
            </g:if>
            <g:else>
              ${productTitle}
            </g:else>
          </h3>

          <g:if test="${product?.images.size() > 0}">
              <g:if test="${product?.content}">
                <g:link controller="shop" action="display" id="${product.id}">
                    <img src="${createLink(controller: 'image', action: 'thumbnail', id: product.images[0].id)}" title="${productTitle}" alt="${productTitle}"/>                
                </g:link>
              </g:if> 
              <g:else>         
                <a href="#">
                  <img src="${createLink(controller: 'image', action: 'thumbnail', id: product.images[0].id)}" title="${productTitle}" alt="${productTitle}"/>
                </a>
              </g:else>
          </g:if>
          <g:else>
              <a href="#">
                  <img class="defaultImg">&nbsp;</img>
              </a>
          </g:else>          
          
          <p>
            ${product?.summary?.encodeAsTextile()}
          </p>
        </li>
      </g:each>

    </g:if>
  </ul>
</div>
