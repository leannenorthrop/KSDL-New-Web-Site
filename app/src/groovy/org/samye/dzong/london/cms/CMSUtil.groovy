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

class CMSUtil {
    private static int MIN = 30
    private static int MAX = 200

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
            flash.message = "${(delegate.controllerName - 'Controller').capitalize()} not found"
            flash.isError = true
            redirect(action:action)
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

        artefactClass.metaClass.getModelForView = { viewName, params ->
            def domainName = (delegate.controllerName - 'Controller')
            params.offset = params?.offset ? params.offset.toInteger() : 0
            params.max = Math.min(params?.max ? params.max.toInteger() : MIN, MAX)

            def model = []
            if (SecurityUtils.subject.hasRoles(delegate.ADMIN_ROLES).any()) {
                model = delegate."${domainName}Service"."${viewName}"(params)
            } else {
                model = delegate."${domainName}Service"."user${viewName.capitalize()}"(params)
            }
            model
        }
    }
}

