package org.samye.dzong.london.contact
import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH
import com.icegreen.greenmail.util.*

class EmailService {
    def servletContext = SCH.servletContext
    boolean transactional = false

    def sendPasswordReset(email, token) {
        sendMail {
          to email
          subject "Forgot your password?"
          body """Hi,

          You recently requested a new password.

          To reset your password, just click the link below and you'll be taken to a form where you can change your password:

          http://localhost:8080/main/auth/doPasswordReset/${token}

          If you did not request resetting of your password, please disregard this e-mail.

          Please contact support@lsd.org with any questions.

          London Samye Dzong"""
        }
        if (servletContext && servletContext.getAttribute("greenmail")) {
            def messages = servletContext.getAttribute("greenmail").getReceivedMessages().each {
                println "Recieved ${GreenMailUtil.toString(it)}"
            }
        }
    }
    def sendAccountVerification(email, token) {
        sendMail {
          to email
          subject "London Samye Dzong: Account Verification"
          body """Hi,

          You recently requested a new account.

          To enable your, just click the link below and you'll be taken to a form where you can request particular access rights:

          http://localhost:8080/main/auth/acl/${token}

          If you did not request an account, please disregard this e-mail.

          Please contact support@lsd.org with any questions.

          London Samye Dzong"""
        }
        if (servletContext && servletContext.getAttribute("greenmail")) {
            def messages = servletContext.getAttribute("greenmail").getReceivedMessages().each {
                println "Recieved ${GreenMailUtil.toString(it)}"
            }
        }
    }
}
