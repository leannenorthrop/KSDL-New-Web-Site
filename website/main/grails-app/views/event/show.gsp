
<%@ page import="org.samye.dzong.london.events.Event" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.publishState.label" default="Publish State" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "publishState")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.author.label" default="Author" /></td>
                            
                            <td valign="top" class="value"><g:link controller="shiroUser" action="show" id="${eventInstance?.author?.id}">${eventInstance?.author?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.displayAuthor.label" default="Display Author" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${eventInstance?.displayAuthor}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.displayDate.label" default="Display Date" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${eventInstance?.displayDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.datePublished.label" default="Date Published" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${eventInstance?.datePublished}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${eventInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${eventInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.deleted.label" default="Deleted" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${eventInstance?.deleted}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.category.label" default="Category" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "category")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.title.label" default="Title" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "title")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.summary.label" default="Summary" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "summary")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.content.label" default="Content" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "content")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.isRepeatable.label" default="Is Repeatable" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${eventInstance?.isRepeatable}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.image.label" default="Image" /></td>
                            
                            <td valign="top" class="value"><g:link controller="image" action="show" id="${eventInstance?.image?.id}">${eventInstance?.image?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.eventDate.label" default="Event Date" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "eventDate")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.startTime.label" default="Start Time" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "startTime")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.eventDuration.label" default="Event Duration" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "eventDuration")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.prices.label" default="Prices" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${eventInstance.prices}" var="p">
                                    <li><g:link controller="eventPrice" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="event.auditLogService.label" default="Audit Log Service" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: eventInstance, field: "auditLogService")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${eventInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
