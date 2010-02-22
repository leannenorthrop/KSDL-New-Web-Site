<html>
  <head>
    <title><g:message code="home"/></title>
    <meta name="layout" content="main">
    <feed:meta kind="rss" version="2.0" controller="feed" action="news"/>
    <feed:meta kind="rss" version="2.0" controller="feed" action="meditation"/>
    <feed:meta kind="rss" version="2.0" controller="feed" action="community"/>
    <feed:meta kind="rss" version="2.0" controller="feed" action="wellbeing"/>

  </head>
  <body>
    <div class="grid_12">
      <g:render template="/eventlist" model="[events: events, heading: 'home.events']"/>
    </div>
    <div class="grid_4">
      <g:render template="/shortnewslist" model="[articles: newsArticles, heading: 'home.news']"/>
      <div class="box">
        <h2><g:message code="service.header"/></h2>
        <ul class="services">
          <li class="email"><g:link controller="feed" action="index"><g:message code="service.email"/></g:link></li>
          <li class="rss"><g:link controller="feed" action="index"><g:message code="service.rss"/></g:link></li>
          <li class="calendar"><g:link controller="feed" action="index"><g:message code="service.calendar"/></g:link></li>
          <li class="twitter"><a href="http://twitter.com/lsdci"><g:message code="service.twitter"/></a></li>
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
  </body>
</html>
