<%--
Template for shop public and management pages.
    User: Leanne
Date: Jan 24, 2010, 2:00:21 PM
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<!------------------------------------------------------------------------------
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
- If not, see http://www.gnu.org/licenses/.
-
- BT plc, hereby disclaims all copyright interest in the program
- “Samye Content Management System” written by Leanne Northrop.
------------------------------------------------------------------------------->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.media.Image" %>
<%@ page import="org.samye.dzong.london.site.Setting" %>
<%@ page import="org.samye.dzong.london.venue.Venue" %>
<g:if test="${params.theme}">
  <g:set var="cssThemesDir" value="${params.theme}"/>
</g:if>
<g:else>
  <g:set var="cssThemesDir" value="${Setting.findByName('DefaultTheme').value}"/>
</g:else>

<html>
  <head>
  <g:set var="title"><g:layoutTitle/></g:set>
  <g:if test="${controllerName == 'news' && actionName == 'home'}">
    <g:set var="title">News</g:set>
  </g:if>
  <g:elseif test="${controllerName == 'home'}">
    <g:set var="title">Kagyu Tibetan Buddhist Center in London</g:set>
  </g:elseif>
  <title><g:message code="title" default="Kagyu Samye Dzong London"/>: ${title}</title>
  <meta http-equiv="X-UA-Compatible" content="IE=8" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="ROBOTS" content="ALL" />
  <meta name="google-site-verification" content="bTJswZnvIBsrjhqIxlpIXIfvXa_wn7McR9PbSgwo_4Q" />
  <meta name="keywords" content="${keywords?.join(',')}" />
  <![if gte IE 7]>
  <link rel="stylesheet" type="text/css" media="screen, projection" href="${resource(dir: 'css/site', file: 'screen.css')}"/>
  <link rel="stylesheet" type="text/css" media="screen, projection" href="${resource(dir: 'css/themes/' + cssThemesDir, file: 'screen.css')}"/>
  <![endif]>
  <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
  <g:javascript src="jquery/jquery-1.4.2.min.js"/>
  <g:javascript src="jquery/jquery-ui-1.8.2.min.js"/>
  <g:javascript src="jquery/jquery.validate.min.js"/>
  <g:layoutHead/>
</head>
<body class="shop">
  <div id="watermark1"></div>
  <div id="watermark2"></div>
  <div id="watermark3"></div>
  <div id="watermark4"></div>
  <div class="container_16">
    <div class="grid_16 banner">
      <g:if test="${Setting.findByName('Logo').value}">
        <img src="${createLink(controller: 'image', action: 'src', id: Setting.findByName('Logo').value)}" title="Logo" alt="Logo"/>
      </g:if>
      <h1>
        <g:message code="title" default="Kagyu Samye Dzong London"/>
      </h1>
      <h2>
        <g:message code="subtitle" default="Tibetan Buddhist Centre for World Peace and Health"/>
      </h2>
    </div>
    <div class="clear"></div>

    <div class="grid_16">
      <lsdc:nav current="${controllerName}"/>
    </div>
    <div class="clear"></div>

    <div class="grid_16">
      <g:set var="siteTitle"><g:message code="title" default="Kagyu Samye Dzong London"/></g:set>
      <g:set var="title"><g:layoutTitle/></g:set>
      <h2 id="page-heading">
        <g:if test="${title.contains(siteTitle)}">
${title.substring(siteTitle.size()+2)}
        </g:if>
        <g:else>
${title}
        </g:else>
      </h2>
    </div>
    <div class="clear"></div>

    <div class="grid_3">
      <g:render template="/shopInfoMenu" model="[articles:featuredArticles]"/>
      <g:render template="/shopMenu" model="[menu:menu]"/>
    </div>
    <div class="grid_13 pagecontent">
      <g:layoutBody/>
    </div>
    <div class="clear"></div>


    <div class="clear"></div>
    <div class="grid_16" id="footer">
      <div class="contact">
        <g:set var="venueId" value="${Setting.findByName('DefaultVenue').value}"/>
        <g:if test="${venueId}">
          <g:set var="v" value="${Venue.get(venueId)}"/>
          <g:if test="${v}">
            <g:each var="address" in="${v.addresses}">
              <g:if test="${address.type == 'main'}">
                <span class="address">
                  <g:if test="${address.placeName}">
${address.placeName}<br/>
                  </g:if>
${address.streetNumber} ${address.line1}<br/>
                  <g:if test="${address.line2}">
${address.line2}<br/>
                  </g:if>
                  <g:if test="${address.postTown}">
${address.postTown}<br/>
                  </g:if>
                  <g:if test="${address.county}">
${address.county}<br/>
                  </g:if>
${address.country}<br/>
                </span>
              </g:if>
            </g:each>
            <g:each var="telephone" in="${v.telephoneNumbers}">
              <g:if test="${telephone.type == 'main'}">
                <span class="telephone">
${telephone}
                </span>
              </g:if>
            </g:each>
          </g:if>
        </g:if>
      </div>
      <div class="box">
        <g:set var="year"><g:formatDate format="yyyy" date="${new Date()}"/></g:set>
        <g:message code="footer.copyright" args="${[year]}"/> <g:message code="title" default="Kagyu Samye Dzong London"/> |
        <g:link controller="aboutUs" action="contactUs"><g:message code="footer.contact.us"/></g:link> |
        <g:link controller="aboutUs" action="roomHire"><g:message code="footer.room.hire"/></g:link> |
        <g:link controller="home" action="siteMap"><g:message code="footer.site.map"/></g:link> |
        <g:link controller="home" action="legal"><g:message code="footer.legal" default="Legal"/></g:link> |
        <g:link controller="home" action="aboutThisSite"><g:message code="footer.about.this.site"/></g:link>
      </div>
    </div>
    <div class="clear"></div>

  </div>
<g:javascript>
  var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
  document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
    try {
        var pageTracker = _gat._getTracker("UA-13271764-1");
        pageTracker._trackPageview();
    } catch(err) {}
    </g:javascript>
  </body>
</html>

