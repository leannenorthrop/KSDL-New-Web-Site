<div class="box">
    <h3><g:link controller="news" action="home"><g:message code="news.heading"/></g:link></h3>
    <ul class="menu">
        
        <li class="menuitem"><g:link controller="news" action="current"><g:message code="news.current.title"/></g:link>
            <ul>
                <g:if test="${months}">
                    <g:each var="monthYear" in="${months}">
                        <g:set var="month" value="${monthYear.substring(0,monthYear.indexOf(' '))}"/>
                        <g:set var="year" value="${monthYear.substring(monthYear.indexOf(' ')+1)}"/>                        
                        <li class="menuitem"><g:link mapping="news" params="[year:year,month:month.toLowerCase()]">${month}</g:link></li>
                    </g:each>
                </g:if>
            </ul>
        </li>
        <li class="menuitem"><g:link controller="news" action="archived"><g:message code="news.archived.title"/></g:link>
            <ul>
                <g:if test="${years}">
                    <g:each var="year" in="${years}">
                        <li class="menuitem"><g:link mapping="news" params="[year:year]">${year}</g:link></li>
                    </g:each>
                </g:if>
            </ul>
        </li>             
    </ul>
</div>