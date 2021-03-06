/*
 * Copyright © 2010 Leanne Northrop
 *
 * This file is part of Samye Content Management System.
 *
 * Samye Content Management System is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * Samye Content Management System is distributed in the hope that it will be
 * useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Samye Content Management System.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * BT plc, hereby disclaims all copyright interest in the program
 * “Samye Content Management System” written by Leanne Northrop.
 */
 
package org.samye.dzong.london.cms  

import org.apache.shiro.SecurityUtils
import org.samye.dzong.london.users.ShiroUser

/**
 * Helper class to inject CMS management methods into a given controller 
 * class. Most methods rely on the given controller class to be extended
 * from CMSController.
 * 
 * @author Leanne Northrop
 * @since  16th November 2010
 */
class CMSUtil {
    private static int MIN = 30
    private static int MAX = 200
    protected static GRAILS_APPLICATION

    
    def static addFinderMethods(artefactClass, log=null) {
        CMSUtil.GRAILS_APPLICATION?.domainClasses.each { domainClass ->
            if (Publishable.isAssignableFrom(domainClass.clazz)) { 
                ['Buddhist':'B','AboutUs':'A','Community':'C','Meditation':'M','WellBeing':'W','News':'N'].each { section,category ->
                    ["Unpublished","Published","Archived","Ready"].each { publicationState -> 
                        ['home','featured','deleted','all'].each { limit ->
                            artefactClass.metaClass."find${publicationState}${section}${limit.capitalize()}${domainClass.name}s" = { params ->
                                try {
                                    def domain = domainClass.clazz        
                                    def list = Publishable.publishStateByCategory(publicationState,category).list(params)
                                    def found = list.findAll { 
                                        (limit == 'all' || it."$limit") && domain.isAssignableFrom(it.class)
                                    }
                                    if (domainClass.name == 'Event'){
                                        found.sort();
                                    }
                                    def all = Publishable.publishStateByCategory(publicationState,category).list()
                                    all = all.findAll { 
                                        (limit == 'all' || it."$limit") && domain.isAssignableFrom(it.class)
                                    }
                                    def total = all.size()
                                    return [(limit + domainClass.propertyName.capitalize() + 's'): found, ('total' + limit.capitalize() + domainClass.propertyName.capitalize() + 's'): total]
                                } catch (error) {
                                    error.printStackTrace()
                                    return [(limit + domainClass.propertyName.capitalize() + 's'): [], ('total' + limit.capitalize() + domainClass.propertyName.capitalize() + 's'): 0]
                                }
                            }
                        }
                    }
                }
            }

            artefactClass.metaClass.findPublishedHomeAllArticles = { params ->
                try {
                    def domain = domainClass.clazz        
                    def list = Publishable.publishStateByCategory(publicationState,category).list(params)
                    def found = list.findAll { 
                        domain.isAssignableFrom(it.class)
                    }
                    def all = Publishable.publishStateByCategory(publicationState,category).list(params)
                    all = all.findAll { 
                        domain.isAssignableFrom(it.class)
                    }
                    def total = all.size()
                    return [allArticles: found, totalAllArticles: total]
                } catch (error) {
                    return [allArticles: [], totalAllArticles: 0]
                }
            }            
            
            artefactClass.metaClass."view${domainClass.name}" = { id ->             
                if (id) {
                    def obj = domainClass.clazz.get(id)
                    if(!obj) {
                        delegate.notFound(null)
                        return [:]
                    }
                    else {
                        try {
                            def similar = Publishable.similar(obj,params)
                            return [(domainClass.propertyName): obj, id: obj.id, similar:similar]
                        } catch (error) {
                            return [(domainClass.propertyName): obj, id: obj.id, similar:[]]
                        }
                    }
                } else {
                    delegate.notFound(null)
                }
            }    
            artefactClass.metaClass.notFound = { action ->
                def name = (delegate.controllerName - 'Controller').capitalize()
                flash.message = "${domainClass.name} not found"
                flash.isError = true
                if (action) {
                    redirect(action:action)
                } else {
                    redirect(controller: 'home', action: 'notFound')
                }
            }                    
        }        
    }
    
    def static addCMSMethods(artefactClass, log=null) {
        if (log) {
            log.info "Adding CMS methods to: ${artefactClass}..."
        } else {
            println "Adding CMS methods to: ${artefactClass}..."
        }
        
        artefactClass.metaClass.rollback = { status, msg, Object[] args ->
            def noOfArgs = args.size()
            if (args && noOfArgs >= 2) {
                delegate.log.warn msg, args[1]
            } else {
                delegate.log.warn msg
            }
            status?.setRollbackOnly()
            flash.message = msg 
            flash.isError = true
            if (args && noOfArgs < 3) {
                flash.bean = args[0]
                flash.args = [args[0]]
            } else if (args && noOfArgs >= 3) {
                flash.bean = args[0]
                flash.args = [args[2]]
            }
        }

        artefactClass.metaClass.notFound = { action ->
            def name = (delegate.controllerName - 'Controller').capitalize()
            flash.message = "${name} not found"
            flash.isError = true
            if (action) {
                redirect(action:action)
            } else {
                redirect(controller: 'home', action: 'notFound')
                //render(status: 404, text: 'Failed to find ${name}')
            }
        }

        artefactClass.metaClass.handleError = { msg, obj, Object[] args ->
            flash.message = msg
            flash.isError = true
            flash.args = args.size() >= 3 ? args[2] : obj ? [obj] : null
            if (obj) {
                flash.bean = obj 
            }
            if (args) {
                if (args.size() == 1) {
                    if (obj && obj?.id) {
                        redirect(action:args[0],id:obj.id)
                    } else {
                        redirect(action:args[0])
                    }
                } else if (args.size() >= 2) {
                    render(view: args[0], model:args[1])
                } 
            }
        }

        artefactClass.metaClass.versionCheck = { params, obj ->
            def ok = true
            if (params.version) {
                def version = params.version.toLong()
                ok = obj.version <= version
                if (!ok) {
                    obj.errors.rejectValue("version", "optimistic.locking.failure", "Another user has updated whilst you were editing.")
                    flash.message = "Changes could not be saved because of the following:"	
                    flash.isError = true
                    flash.bean = obj 
                    flash.args = [obj]
                }
            }
            ok
        }

        artefactClass.metaClass.getModelForView = { publicationState, params ->
            def domainName = (delegate.controllerName - 'Controller').capitalize()
            params.offset = params?.offset ? params.offset.toInteger() : 0
            params.max = Math.min(params?.max ? params.max.toInteger() : MIN, MAX)

            def model = []
            if (SecurityUtils.subject.hasRoles(delegate.ADMIN_ROLES).any()) {
                println "${publicationState}${domainName}s"
                model = delegate."${publicationState}${domainName}s"(params)
            } else {
                println "${publicationState}${domainName}s"                
                model = delegate."user${publicationState.capitalize()}${domainName}s"(params)
            }
            model
        }
        
        artefactClass.metaClass.currentUser = {
            def subject = SecurityUtils?.subject
            if (subject && subject?.principal) {
                ShiroUser.findByUsername(subject.principal)
            }
        } 
                        
        CMSUtil.GRAILS_APPLICATION?.domainClasses.each { domainClass ->
             if (Publishable.isAssignableFrom(domainClass.clazz)) { 
                 artefactClass.metaClass."view${domainClass.name}" = { id ->             
                    if (id) {
                        def obj = domainClass.clazz.get(id)
                        if(!obj) {
                            delegate.notFound(null)
                            return [:]
                        }
                        else {
                            try {
                                def similar = Publishable.similar(obj,params)
                                return [(domainClass.propertyName): obj, id: obj.id, similar:similar]
                            } catch (error) {
                                return [(domainClass.propertyName): obj, id: obj.id, similar:[]]
                            }
                        }
                    } else {
                        delegate.notFound(null)
                    }
                }
                
                ["Unpublished","Published","Archived","Ready"].each { state -> 
                    artefactClass.metaClass."user${state}${domainClass.name}s" = { params ->
                        try {
                            delegate.checkParams(params)
                            def username = delegate.currentUser().username;
                            def domain = domainClass.clazz
                            def objs = domain.authorPublishState(username,state).list(params)
                            def total = domain.authorPublishState(username,state).count()
                            return [(domainClass.propertyName + 's'): objs, total: total]
                        } catch (error) {
                            return [(domainClass.propertyName + 's'): [], total: []]
                        }
                    }
                }

                artefactClass.metaClass."userDeleted${domainClass.name}s" = { params -> 
                    try {
                        delegate.checkParams(params)
                        def username = delegate.currentUser().username
                        def domain = domainClass.clazz
                        def objs = domain.authorDeleted(username).list(params)
                        def total = domain.authorDeleted(username).count()
                        return [(domainClass.propertyName + 's'): objs, total: total]
                    } catch (error) {
                        return [(domainClass.propertyName + 's'): [], total: []]                        
                    }
                }

                ["Unpublished","Published","Archived","Ready"].each { state -> 
                    artefactClass.metaClass."${state.toLowerCase()}${domainClass.name}s" = { params -> 
                        try {
                            delegate.checkParams(params)
                            def domain = domainClass.clazz
                            def objs = domain.publishState(state).list(params)
                            def total = domain.publishState(state).count()
                            return [(domainClass.propertyName + 's'): objs, total: total]
                        } catch (error) {
                            delegate.log.error("Unable to find ${state.toLowerCase()}${domainClass.name}s due to ${error}",error)
                            return [(domainClass.propertyName + 's'): [], total: []]
                        }
                    }
                }

                artefactClass.metaClass."deleted${domainClass.name}s" = { params -> 
                    try {
                        delegate.checkParams(params)
                        def domain = domainClass.clazz
                        def rooms = domain.deleted().list(params)
                        def total = domain.deleted().count()
                        return [(domainClass.propertyName + 's'): rooms, total: total]
                    } catch (error) {
                        return [(domainClass.propertyName + 's'): [], total: []]                        
                    }
                }                        

                artefactClass.metaClass.filter = { list, domain ->
                    list.findAll{ domain.isAssignableFrom(it.class) }
                }

                artefactClass.metaClass.checkParams = { params ->
                    def metaClazz = delegate.metaClass
                    if (!params?.sort){
                        if (metaClazz.hasProperty(delegate,"default${domainClass.name}Sort")) {
                            params.sort = delegate."default${domainClass.name}Sort"
                        }
                    }
                    if (params?.order){
                        if (metaClazz.hasProperty(delegate,"default${domainClass.name}Order")) {
                            params.order = delegate."default${domainClass.name}Order"
                        }
                    }
                }
             }
         }                 
    }
}

