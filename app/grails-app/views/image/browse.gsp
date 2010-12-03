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
Browse images using Ajax carosel
User: Leanne Northrop
Date: Jan 25, 2010, 6:04:58 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
  <head>
    <meta name="layout" content="content-admin" />
    <title>Manage Images</title>
    <link rel="stylesheet" href="${resource(dir:'css/site',file:'imageCarousel.css')}" media="screen, projection" />
  <g:javascript src="jquery/jquery.ImageCarousel.js"/>
  <g:javascript>
    var srcImage;
    var title;
    var show = function(e) {
    var node = $(".imagedisplay");
    var imgNode = $(".imagedisplay img");
    var titleNode = $(".imagedisplay h3");
    imgNode.attr({src: srcImage.replace("thumbnail", "src")});
    titleNode.html(title);
    node.fadeIn({duration: 500, easing: "easeInOutQuad"});
    };
    var hide = function(e) {
    srcImage = e.target.src;
    title = e.target.title;
    $(".imagedisplay").fadeOut({duration: 500, easing: "easeInOutQuad", complete: show});
    };

    $(document).ready(function() {
    $(".carouselBody li a img").mouseenter(function(e){hide(e);});

    $('.carousel').ImageCarousel(  {
    carousel_width: '100%',
    display_header: false,
    carousel_speed: 'normal'
    });
    });
  </g:javascript>
</head>
<body>
  <div class="content">
    <h1>Image List</h1>
    <p>Hover mouse over thumbnail images to preview, click thumbnail to edit tags. Use left and right arrow buttons to scroll through images.</p>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="jquery-ui carousel">
      <div class="carouselBody">
        <ul>
          <g:each in="${imageInstanceList}" status="i" var="imageInstance">
            <li>
            <g:link action="edit" id="${imageInstance.id}" style="width:130px;height:130px;text-align:center;vertical-align:middle;">
              <img src="${createLink(controller:'image', action:'thumbnail', id:imageInstance.id)}" alt="${fieldValue(bean:imageInstance, field:'name')}" title="${fieldValue(bean:imageInstance, field:'name')}"/>
            </g:link>
            </li>
          </g:each>
        </ul>
        <a href="#" class="btnNext" style="color: red;">Next</a>
        <a href="#" class="btnPrevious" style="color: red;">Previous</a><br/>
        <div class="imagedisplay" style="height:420px;width:100%;clear:both;margin:0.5em;text-align:center;vertical-align:middle;">
          <h3></h3>
          <img height="400px" src="${createLink(controller:'image', action:'src', id:imageInstanceList[0]?.id)}"/>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
