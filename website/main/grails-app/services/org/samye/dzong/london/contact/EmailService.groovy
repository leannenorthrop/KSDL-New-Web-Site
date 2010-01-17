package org.samye.dzong.london.contact

class EmailService {

    boolean transactional = false

    def sendPasswordReset(email) {
        sendMail {
          to email
          subject "Forgot your password?"
          body """Hi,

          You recently requested a new password.

          To reset your password, just click the link below and you'll be taken to a form where you can reset your password:

          http://localhost:8080/main/auth/doPasswordReset/${email}

          If you did not reset your password, please disregard this e-mail.

          Please contact support@lsd.org with any questions.

          London Samye Dzong"""
        }
    }
}
