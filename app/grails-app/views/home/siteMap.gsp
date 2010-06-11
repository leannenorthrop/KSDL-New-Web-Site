<html>
<head>
    <title><g:message code="footer.site.map"/></title>
    <meta name="layout" content="main">
</head>
<body>    
    <div class="grid_16 class" id="site-map">
			<ul class="menu"> 
				<li>
				    <g:link controller="home" action="index" class="menuitem"><g:message code="home"/></g:link> 
				</li>			    
				<li> 
					<g:link controller="aboutUs" action="index" class="menuitem"><g:message code="aboutUs"/></g:link> 
					<ul class="submenu"> 
						<li> 
							<g:link controller="aboutUs" action="visiting" class="menuitem"><g:message code="footer.visit.us"/></g:link> 
						</li> 
						<li> 
							<g:link controller="aboutUs" action="contactUs" class="menuitem"><g:message code="footer.contact.us"/></g:link> 
						</li> 
						<li> 
							<g:link controller="aboutUs" action="lineage" class="menuitem"><g:message code="teacher.category.L"/></g:link> 
						</li>						
						<li> 
							<g:link controller="aboutUs" action="teachers" class="menuitem"><g:message code="teacher.heading.title"/></g:link> 
						</li> 
					</ul> 
				</li> 
				<li> 
					<g:link controller="news" action="home" class="menuitem"><g:message code="news"/></g:link>  
					<ul class="submenu"> 
						<li> 
							<g:link controller="news" action="current" class="menuitem"><g:message code="news.current.title"/></g:link> 
						</li> 
						<li> 
							<g:link controller="news" action="archived" class="menuitem"><g:message code="news.archived.title"/></g:link> 
						</li>
					</ul> 
				</li> 
				<li> 
					<g:link controller="event" action="home" class="menuitem"><g:message code="event"/></g:link>  
					<ul class="submenu"> 
						<li> 
							<g:link controller="event" action="current" class="menuitem"><g:message code="event.this.month"/></g:link> 
						</li> 
						<li> 
							<g:link controller="event" action="future" class="menuitem"><g:message code="home.events"/></g:link> 
						</li>		
						<li> 
							<g:link controller="event" action="regular" class="menuitem"><g:message code="event.regular"/></g:link> 
						</li>										
					</ul> 
				</li>
				<li> 
					<g:link controller="meditation" action="home" class="menuitem"><g:message code="meditation"/></g:link>  
					<ul class="submenu"> 
						<li> 
							<g:link controller="meditation" action="all" class="menuitem"><g:message code="meditation.articles.title"/></g:link> 
						</li> 
						<li> 
							<g:link controller="meditation" action="events" class="menuitem"><g:message code="event.meditation"/></g:link> 
						</li>												
					</ul> 
				</li>	
				<li> 
					<g:link controller="community" action="home" class="menuitem"><g:message code="community"/></g:link>  
					<ul class="submenu"> 
						<li> 
							<g:link controller="community" action="list" class="menuitem"><g:message code="community.articles.heading"/></g:link> 
						</li> 
						<li> 
							<g:link controller="community" action="events" class="menuitem"><g:message code="event.community"/></g:link> 
						</li>						
					</ul> 
				</li>
				<li> 
					<g:link controller="wellbeing" action="home" class="menuitem"><g:message code="wellbeing"/></g:link>  
					<ul class="submenu"> 
						<li> 
							<g:link controller="wellbeing" action="list" class="menuitem"><g:message code="wellbeing.articles.heading"/></g:link> 
						</li> 
						<li> 
							<g:link controller="wellbeing" action="events" class="menuitem"><g:message code="event.wellbeing"/></g:link> 
						</li>
					</ul> 
				</li>	
				<li> 
					<g:link controller="buddhism" action="home" class="menuitem"><g:message code="buddhism"/></g:link>  
					<ul class="submenu"> 
						<li> 
							<g:link controller="buddhism" action="list" class="menuitem"><g:message code="buddhism.articles.title"/></g:link> 
						</li> 
						<li> 
							<g:link controller="buddhism" action="events" class="menuitem"><g:message code="event.buddhism"/></g:link> 
						</li>
					</ul> 
				</li>														
			</ul> 
    </div>
</body>
</html>