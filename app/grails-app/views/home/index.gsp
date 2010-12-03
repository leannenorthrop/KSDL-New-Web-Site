<%@ page import="org.samye.dzong.london.site.Link;org.samye.dzong.london.site.Setting" contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title><g:message code="welcome" default="Welcome"/></title>
    <meta name="layout" content="main">
    <link rel="stylesheet" type="text/css" media="screen, projection" href="${resource(dir: 'css/site/slideshow', file: 'slideshow.css')}"/> 
    <g:javascript src="jquery/jquery-1.4.2.min.js"/>
    <g:javascript src="jquery/jquery-ui-1.8.2.min.js"/>
    <g:javascript src="jquery/jquery.validate.min.js"/> 
    <g:javascript src="jquery/cookie.js"/>      
    <jq:jquery>
        var a = $.cookie("site_msg");
        if (a) {
            $("#site_msg").hide();
        }
        $("#site_msg").click(function() {
            $.cookie("site_msg", "true", { expires: 1 });
            $(this).hide("slow");
        });
    </jq:jquery>      
  </head>
  <body>
      <g:if test="${Setting.findByName('SiteMessage').value}">
        <div id="site_msg">
            <span class="close">&nbsp;</span>
            ${Setting.findByName('SiteMessage').value.encodeAsTextile()}
        </div>
      </g:if>      
    <div class="grid_12">
        <g:render template="/toparticles" model="[articles:topArticles]"/>
    </div>
    <div class="grid_4">
          <g:render template="/slideshowLink" model="[album:album,relUrl:'home/slideshow']"/>          
    </div>      
    <div class="clear"></div>        
    <div class="grid_12">    
      <g:render template="/shortEventsList" model="[events: events, heading: 'home.events']"/>
    </div>
    <div class="grid_4">
      <g:render template="/shortnewslist" model="[articles: newsArticles, heading: 'home.news']"/>
      <div class="clear"></div>
      <div class="box services">
        <h3><g:message code="service.header"/></h3>
        <ul class="menu">
            <g:each var="link" in="${links}">
                <li>${link}</li>
            </g:each>
        </ul>
      </div>
    </div>
    <div class="clear"></div>
     <div class="grid_8">
      <g:render template="/toparticles" model="[articles:meditationArticles,controller:'meditation']"/>
    </div>
    <div class="grid_8">
      <g:render template="/toparticles" model="[articles:communityArticles,controller:'community']"/>
    </div>
    <div class="clear"></div>
    <div class="grid_8">
      <g:render template="/toparticles" model="[articles:buddhismArticles,controller:'buddhism']"/>
    </div>
    <div class="grid_8">
      <g:render template="/toparticles" model="[articles:wellbeingArticles,controller:'wellbeing']"/>
    </div>    
    <div class="clear"></div>    
  </body>
</html>
