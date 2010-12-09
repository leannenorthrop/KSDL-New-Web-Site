<div class="box">
    <h3><g:link controller="aboutUs" action="home"><g:message code="aboutUs"/></g:link></h3>
    <ul class="menu">
        <li class="menuitem"><g:link controller="aboutUs" action="list">Information</g:link>
            <ul>
                <g:if test="${featuredArticles}">
                    <g:each var="article" in="${featuredArticles}">
                        <li class="menuitem"><g:link controller="aboutUs" action="view" id="${article.id}" class="menuitem">${article?.title}</g:link></li>
                    </g:each>
                </g:if>
            </ul>
        </li>                
        <li class="menuitem"><g:link controller="aboutUs" action="contactUs">Teachers</g:link>
            <ul>
                <li class="menuitem"><g:link controller="aboutUs" action="lineage"><g:message code="teacher.lineage.heading.title"/></g:link>
                    <g:if test="${lineage}">
                        <ul>                        
                            <g:each var="teacher" in="${lineage}">
                                <li class="menuitem"><g:link controller="aboutUs" action="teacher" id="${teacher?.id}" class="menuitem">${teacher}</g:link></li>
                            </g:each>          
                        </ul>                            
                    </g:if>                    
                </li>
                <li class="menuitem"><g:link controller="aboutUs" action="teachers"><g:message code="teacher.center.heading.title"/></g:link>
                    <g:if test="${teachers}">
                        <ul>                        
                            <g:each var="teacher" in="${teachers}">
                                <li class="menuitem"><g:link controller="aboutUs" action="teacher" id="${teacher?.id}" class="menuitem">${teacher}</g:link></li>
                            </g:each>          
                        </ul>                            
                    </g:if>                      
                </li>
                <li class="menuitem"><g:link controller="aboutUs" action="visiting"><g:message code="teacher.visiting.heading.title"/></g:link>
                    <g:if test="${visitingTeachers}">
                        <ul>                        
                            <g:each var="teacher" in="${visitingTeachers}">
                                <li class="menuitem"><g:link controller="aboutUs" action="teacher" id="${teacher?.id}" class="menuitem">${teacher}</g:link></li>
                            </g:each>          
                        </ul>                            
                    </g:if>                     
                </li>                
            </ul>
        </li>
        <li class="menuitem"><g:link controller="aboutUs" action="centers">Centers</g:link>
            <ul>
                <g:if test="${venues}">
                    <g:each var="venue" in="${venues}">
                        <li class="menuitem"><g:link controller="aboutUs" action="venue" id="${venue?.id}" class="menuitem">${venue}</g:link></li>
                    </g:each>          
                </g:if>
            </ul>
        </li>
        <li class="menuitem"><g:link controller="room" action="home"><g:message code="footer.room.hire"/></g:link></li> 
        <li class="menuitem"><g:link controller="aboutUs" action="contactUs"><g:message code="footer.contact.us"/></g:link></li>
    </ul>
</div>