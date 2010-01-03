<html>
<body>
    <table>
        <thead>
            <tr>
                <g:sortableColumn property="title" title="Title" />
                <th>Type</th>
                <g:sortableColumn property="publishedOn" title="Published On" />
                <g:sortableColumn property="lastUpdated" title="Last Updated On" />
                <shiro:hasAnyRole in="['Editor','Administrator']">
                    <th>Author</th>
                </shiro:hasAnyRole>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <g:each in="${articles}" status="i" var="articleInstance">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                <td>
                    <g:link action="show" id="${articleInstance.id}">${fieldValue(bean:articleInstance, field:'title')}</g:link>
                </td>
                <td>
                    <g:findAll in="${articleInstance.tags}" expr="it == 'news'">News
                    </g:findAll>
                    <g:findAll in="${articleInstance.tags}" expr="it == 'meditation'">Meditation
                    </g:findAll>
                    <g:findAll in="${articleInstance.tags}" expr="it == 'community'">Community
                    </g:findAll>
                    <g:findAll in="${articleInstance.tags}" expr="it == 'wellbeing'">Well Being
                    </g:findAll>                                                            
                </td>
                <td><g:formatDate format="dd-MM-yyyy mm:HH" date="${articleInstance?.publishedOn}"/></td>
                <td><g:formatDate format="dd-MM-yyyy mm:HH" date="${articleInstance?.lastUpdated}"/></td>
                <shiro:hasAnyRole in="['Editor','Administrator']">
                    <td>${fieldValue(bean:articleInstance, field:'author')}</td>
                </shiro:hasAnyRole>
                <td>
                <shiro:hasAnyRole in="['Editor','Administrator']">
                    <g:link action="changeState" params="[state:'Unpublished']" id="${articleInstance.id}">Un-Publish</g:link>
                    <g:link action="changeState" params="[state:'Archived']" id="${articleInstance.id}">Archive</g:link>                    
                </shiro:hasAnyRole>                                
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>      
    <div class="paginateButtons">
        <g:paginate total="${total}" />
    </div>        
</body>
</html>
