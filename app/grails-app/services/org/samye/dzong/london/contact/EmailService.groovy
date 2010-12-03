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

package org.samye.dzong.london.contact

import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH
import com.icegreen.greenmail.util.*

class EmailService {
    def servletContext = SCH.servletContext
    boolean transactional = false

    def sendVolunteerRequest(final fromEmail, final theSubject, final theBody) {
        sendMail {
          to 'volunteer-request@londonsamyedzong.org'
          replyTo fromEmail
          subject "London Samye Dzong: ${theSubject}"
          body theBody
        }
        if (servletContext && servletContext.getAttribute("greenmail")) {
            def messages = servletContext.getAttribute("greenmail").getReceivedMessages().each {
                println "Recieved ${GreenMailUtil.toString(it)}"
            }
        }
    }


    def sendEventQuery(final toEmail, final fromEmail, final theSubject, final theBody) {
        sendMail {
          to toEmail
          replyTo fromEmail
          subject "London Samye Dzong: ${theSubject}"
          body theBody
        }
        if (servletContext && servletContext.getAttribute("greenmail")) {
            def messages = servletContext.getAttribute("greenmail").getReceivedMessages().each {
                println "Recieved ${GreenMailUtil.toString(it)}"
            }
        }
    }

    def sendPasswordReset(email, token, baseUrl) {
        sendMail {
          to email
          subject "London Samye Dzong: Forgot your password?"
          body """Hi,

          You recently requested a new password.

          To reset your password, just click the link below and you'll be taken to a form where you can change your password:

          ${baseUrl}/${token}

          If you did not request resetting of your password, please disregard this e-mail.

          Please contact site@londonsamyedzong.org with any questions.

          London Samye Dzong"""
        }
        if (servletContext && servletContext.getAttribute("greenmail")) {
            def messages = servletContext.getAttribute("greenmail").getReceivedMessages().each {
                println "Recieved ${GreenMailUtil.toString(it)}"
            }
        }
    }

    def sendAccountVerification(email, token, baseUrl) {
        sendMail {
          to email
          subject "London Samye Dzong: Account Verification"
          body """Hi,

          You recently requested a new account.

          To enable your account, just click the link below and you'll be taken to a form where you can request particular access rights:

          ${baseUrl}/${token}

          If you did not request an account, please disregard this e-mail.

          Please contact site@londonsamyedzong.org with any questions.

          London Samye Dzong"""
        }
        if (servletContext && servletContext.getAttribute("greenmail")) {
            def messages = servletContext.getAttribute("greenmail").getReceivedMessages().each {
                println "Recieved ${GreenMailUtil.toString(it)}"
            }
        }
    }
    def sendPermissionsRequest(email, roles) {
        def roleList = ""
        roles.eachWithIndex {item, i -> roleList += "${i}. ${item}\n"}
        sendMail {
          to "leanne.northrop@googlemail.com"
          subject "London Samye Dzong: Permissions Request"
          body """Hi,

          ${email} recently created a new account, and would like the following access rights:

          ${roles}

          Please reply to ${email} either confirming or denying thier request.

          London Samye Dzong"""
        }
        if (servletContext && servletContext.getAttribute("greenmail")) {
            def messages = servletContext.getAttribute("greenmail").getReceivedMessages().each {
                println "Recieved ${GreenMailUtil.toString(it)}"
            }
        }
    }
}
