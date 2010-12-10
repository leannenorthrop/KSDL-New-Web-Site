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
<%@ page import="org.samye.dzong.london.site.Link;org.samye.dzong.london.site.Setting" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title><g:message code="welcome" default="Welcome"/></title>
        <meta name="layout" content="main">
        <link rel="stylesheet" type="text/css" media="screen, projection" href="${resource(dir: 'css/site/slideshow', file: 'slideshow.css')}"/> 
        <g:javascript src="jquery/cookie.js"/>
        <jq:jquery>
        $('#rotating-elements').sideswap();
        var a = $.cookie("site_msg");
        if (a) {$("#site_msg").hide();}
        $("#site_msg").click(function() {$.cookie("site_msg", "true", { expires: 1 });$(this).hide("slow");});
        </jq:jquery>
    </head>
    <body>
        <g:if test="${Setting.findByName('SiteMessage')?.value}">
            <div id="site_msg"><span class="close">&nbsp;</span>${Setting.findByName('SiteMessage')?.value.encodeAsTextile()}</div>
        </g:if>      
        <div class="grid_12">
            <div id="rotating-container">
                <div id="rotating-elements">
                    <g:each in="${topArticles}" status="i" var="articleInstance">
                        <div class="element-rotated">
                            <g:if test="${articleInstance?.image}">
                                <g:if test="${articleInstance?.image?.mimeType.endsWith('png')}">
                                    <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}" class="pngImg"/>
                                </g:if>
                                <g:else>
                                    <img id="articleImage" src="${createLink(controller: 'image', action: 'src', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
                                </g:else>
                            </g:if>
                            <p>${articleInstance?.summary?.encodeAsTextile()}
                                <g:if test="${articleInstance?.content}">
                                    <g:if test="${controller}">
                                        <g:link controller="${controller}" action="view" id="${articleInstance.id}"><g:message code='content.more'/></g:link>
                                    </g:if>
                                    <g:else>
                                        <g:link action="view" id="${articleInstance.id}"><g:message code='content.more'/></g:link>
                                    </g:else>
                                </g:if>  	    
                            </p>
                        </div>
                    </g:each>
                </div>
            </div>    
        </div>      
        <div class="grid_4">
            <div class="box services">
                <h3><g:message code="service.header"/></h3>
                <g:if test="${links}">
                    <ul class="menu">
                        <g:each var="link" in="${links}">
                            <li>${link}</li>
                        </g:each>
                    </ul>
                </g:if>
            </div>
        </div>     
        <div class="clear"></div>     
        <div class="grid_6">
          <g:render template="/homeBox" model="[articles:meditationArticles,controller:'meditation']"/>
        </div>
        <div class="grid_6">
          <g:render template="/homeBox" model="[articles:communityArticles,controller:'community']"/>
        </div> 
        <div class="grid_4">
            <g:render template="/shortnewslist" model="[articles: newsArticles, heading: 'home.news']"/>
        </div>        
        <div class="clear"></div>   
        <div class="grid_6">
          <g:render template="/homeBox" model="[articles:buddhismArticles,controller:'buddhism']"/>
        </div>
        <div class="grid_6">
          <g:render template="/homeBox" model="[articles:wellbeingArticles,controller:'wellbeing']"/>
        </div>    
        <div class="grid_4">
            <g:render template="/shortEventsList" model="[events: events, heading: 'home.events']"/>
        </div>        
        <div class="clear"></div>                   
    </body>
</html>
