<html>
<body>
    <table>
        <thead>
            <tr>
                <g:sortableColumn property="title" title="Title" />
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
                    <g:link action="changeState" params="[state:'Unpublished']" id="${articleInstance.id}">Un-Delete</g:link>                               
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
