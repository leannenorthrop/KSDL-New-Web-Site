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
    <div class="col1_80_Percent">
      <div class="events box">
        <g:render template="/eventlist" model="[events: events, heading: 'home.events']"/>
      </div>
    </div>
    <div class="col2_20_Percent">
      <div class="news box">
        <g:render template="/shortnewslist" model="[articles: newsArticles, heading: 'home.news']"/>
      </div>
      <div class="box">
        <h1><g:message code="service.header"/></h1>
        <ul class="services">
          <li class="email"><g:link controller="feed" action="index"><g:message code="service.email"/></g:link></li>
          <li class="rss"><g:link controller="feed" action="index"><g:message code="service.rss"/></g:link></li>
          <li class="calendar"><g:link controller="feed" action="index"><g:message code="service.calendar"/></g:link></li>
          <li class="twitter"><a href="http://twitter.com/lsdci"><g:message code="service.twitter"/></a></li>
        </ul>
      </div>
    </div>
    <div class="col1_50_Percent">
      <g:render template="/toparticles" model="[articles:meditationArticles]"/>
    </div>
    <div class="col2_50_Percent">
      <g:render template="/toparticles" model="[articles:communityArticles]"/>
    </div>
    <div class="col1_50_Percent">
      <g:render template="/toparticles" model="[articles:buddhismArticles]"/>
    </div>
    <div class="col2_50_Percent">
      <g:render template="/toparticles" model="[articles:wellbeingArticles]"/>
    </div>
  </body>
</html>
