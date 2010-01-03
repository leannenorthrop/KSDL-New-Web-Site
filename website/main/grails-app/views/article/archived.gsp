<html>
<body>
    <table>
        <thead>
            <tr>
                <g:sortableColumn property="title" title="Title" />
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
                <td>${fieldValue(bean:articleInstance, field:'lastUpdated')}</td>
                <shiro:hasAnyRole in="['Editor','Administrator']">
                    <td>${fieldValue(bean:articleInstance, field:'author')}</td>
                </shiro:hasAnyRole>
                <td>
                <shiro:hasAnyRole in="['Editor','Administrator']">
                    <g:link action="delete" id="${articleInstance.id}">Delete</g:link>
                    <g:link action="changeState" params="[state:'Unpublished']" id="${articleInstance.id}">Un-Publish</g:link>                    
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
