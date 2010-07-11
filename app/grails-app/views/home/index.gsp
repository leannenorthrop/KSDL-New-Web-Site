<html>
  <head>
    <title><g:message code="welcome" default="Welcome"/></title>
    <meta name="layout" content="main">
    <feed:meta kind="rss" version="2.0" controller="feed" action="news"/>
    <feed:meta kind="rss" version="2.0" controller="feed" action="meditation"/>
    <feed:meta kind="rss" version="2.0" controller="feed" action="community"/>
    <feed:meta kind="rss" version="2.0" controller="feed" action="wellbeing"/>
    <link rel="stylesheet" type="text/css" media="screen, projection" href="${resource(dir: 'css/site/slideshow', file: 'slideshow.css')}"/> 
  </head>
  <body>
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
        <h2><g:message code="service.header"/></h2>
        <ul class="menu">
          <li class="email menuitem"><a href="http://visitor.constantcontact.com/manage/optin?v=001Qllubg_SeqJPuzfEQO27-PsaMuFhxMTC"><g:message code="service.email"/></a></li>
          <li class="rss menuitem"><g:link controller="home" action="feed"><g:message code="service.rss"/></g:link></li>
          <li class="calendar menuitem"><g:link controller="home" action="calendars"><g:message code="service.calendar"/></g:link></li>
          <li class="donate menuitem"><g:link controller="home" action="donate"><g:message code="home.donate"/></g:link></li>
        </ul>
      </div>
    </div>
    <div class="clear"></div>
     <div class="grid_8">
      <g:render template="/toparticles" model="[articles:meditationArticles]"/>
    </div>
    <div class="grid_8">
      <g:render template="/toparticles" model="[articles:communityArticles]"/>
    </div>
    <div class="clear"></div>
    <div class="grid_8">
      <g:render template="/toparticles" model="[articles:buddhismArticles]"/>
    </div>
    <div class="grid_8">
      <g:render template="/toparticles" model="[articles:wellbeingArticles]"/>
    </div>    
    <div class="clear"></div>    
  </body>
</html>
