<html>
  <head>
    <title><g:message code="welcome" default="Welcome"/></title>
    <meta name="layout" content="main">
    <feed:meta kind="rss" version="2.0" controller="feed" action="news"/>
    <feed:meta kind="rss" version="2.0" controller="feed" action="meditation"/>
    <feed:meta kind="rss" version="2.0" controller="feed" action="community"/>
    <feed:meta kind="rss" version="2.0" controller="feed" action="wellbeing"/>

  </head>
  <body>
      <div class="grid_16">
        <div class="box" id="kwick-box">
            <div id="kwick">
                <ul class="kwicks">
                    <li>
                        <div class="kwick one" href="#">
                            <g:render template="/homeArticle" model="[articles:meditationArticles,controller:'meditation',action:'home']"/>
                        </div>
                    </li>
                    <li>
                        <div class="kwick two" href="#">
                            <g:render template="/homeArticle" model="[articles:communityArticles,controller:'community',action:'home']"/>
                        </div>
                    </li>
                    <li>
                        <div class="kwick three" href="#">
                            <g:render template="/homeArticle" model="[articles:buddhismArticles,controller:'buddhism',action:'home']"/>
                        </div>
                    </li>
                    <li>
                        <div class="kwick four" href="#">
                            <g:render template="/homeArticle" model="[articles:wellbeingArticles,controller:'wellbeing',action:'home']"/>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
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
        <ul>
          <li class="email"><a href="http://visitor.constantcontact.com/manage/optin?v=001Qllubg_SeqJPuzfEQO27-PsaMuFhxMTC"><g:message code="service.email"/></a></li>
          <li class="rss"><g:link controller="home" action="feed"><g:message code="service.rss"/></g:link></li>
          <li class="calendar"><g:link controller="feed" action="index"><g:message code="service.calendar"/></g:link></li>
          <li class="twitter"><a href="http://twitter.com/lsdci"><g:message code="service.twitter"/></a></li>
        </ul>
      </div>
    </div>
    <div class="clear"></div>
    <noscript>
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
    </noscript>
  </body>
</html>
