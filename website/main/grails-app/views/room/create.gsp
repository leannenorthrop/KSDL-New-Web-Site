
<%@ page import="org.samye.dzong.london.venue.Room" %>
<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Add a Room</title>      
    </head>
    <body>
        <div class="content">
            <h1>Create Room</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${roomInstance}">
            <div class="errors">
                <g:renderErrors bean="${roomInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:roomInstance,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:roomInstance,field:'name')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Description:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:roomInstance,field:'description','errors')}">
                                    <input type="text" id="description" name="description" value="${fieldValue(bean:roomInstance,field:'description')}"/>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
