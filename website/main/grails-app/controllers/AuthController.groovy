import org.samye.dzong.london.*
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.web.SavedRequest
import org.apache.shiro.web.WebUtils
import org.apache.shiro.crypto.hash.Sha1Hash
import org.samye.dzong.london.contact.EmailService
import com.icegreen.greenmail.util.*

class AuthController {
    def shiroSecurityManager
    def emailService
    def messageSource
    def greenMail

    def index = { redirect(action: "login", params: params) }

    def login = {
        return [ username: params.username, rememberMe: (params.rememberMe != null), targetUri: params.targetUri ]
    }

    def register = {
        log.trace "Begin register for ${params.username}"
        def username = params.username
        if (ShiroUser.findByUsername(username)) {
            log.trace "Username ${username} is in use."
            flash.message = "register.username.in.use"
            redirect(action: "login", params: params)
        } else if (params.password.equals(params.passwordAgain)) {
            try {
                log.trace "Creating account for ${params.username}"
                def token = new Sha1Hash(new Date().toString()).toHex()
                def newUser = new ShiroUser(username: params.username, passwordHash: new Sha1Hash(params.password).toHex(), passwordReset: token)
                if (!newUser.hasErrors() && newUser.save()) {
                    log.trace "New account created for ${params.username}"
                    emailService.sendAccountVerification(newUser.username,token)
                    flash.message = 'register.success'
                    redirect(controller: 'manageSite', action: 'info')
                } else {
                    log.info "There was a problem creating a new account for ${params.username}"
                    flash.message = 'register.success.internal.error'
                    redirect(action: "login", params: params)
                }
            } catch (Exception e) {
                log.error "There was an error creating a new account for ${params.username}", e
                flash.message = message(code: "login.failed")
                redirect(controller: 'manageSite', action: 'error')
            }
        } else {
            log.trace "Register for ${params.username} passwords do not match."
            flash.message = message(code: "register.passwords.match.failure")
            redirect(action: "login", params: params)
        }
    }

    def signIn = {
        def authToken = new UsernamePasswordToken(params.username, params.password)

        // Support for "remember me"
        if (params.rememberMe) {
            authToken.rememberMe = true
        }

        // If a controller redirected to this page, redirect back
        // to it. Otherwise redirect to the root URI.
        def targetUri = params.targetUri ?: "/"

        // Handle requests saved by Shiro filters.
        def savedRequest = WebUtils.getSavedRequest(request)
        if (savedRequest) {
            targetUri = savedRequest.requestURI - request.contextPath
            if (savedRequest.queryString) targetUri = targetUri + '?' + savedRequest.queryString
        } else {
            targetUri = "/manageSite/home";
        }

        try{
            // Perform the actual login. An AuthenticationException
            // will be thrown if the username is unrecognised or the
            // password is incorrect.
            SecurityUtils.subject.login(authToken)

            log.info "Redirecting to '${targetUri}'."
            redirect(uri: targetUri)
        }
        catch (AuthenticationException ex){
            // Authentication failed, so display the appropriate message
            // on the login page.
            log.info "Authentication failure for user '${params.username}'."
            flash.message = message(code: "login.failed")

            // Keep the username and "remember me" setting so that the
            // user doesn't have to enter them again.
            def m = [ username: params.username ]
            if (params.rememberMe) {
                m["rememberMe"] = true
            }

            // Remember the target URI too.
            if (params.targetUri) {
                m["targetUri"] = params.targetUri
            }

            // Now redirect back to the login page.
            redirect(action: "login", params: m)
        }
    }

    def signOut = {
        // Log the user out of the application.
        SecurityUtils.subject?.logout()

        // For now, redirect back to the home page.
        redirect(uri: "/")
    }

    def unauthorized = {
        flash.message = "error.unauthorized"
        redirect(controller: 'home', action: 'error')
    }

    def onResetPassword = {
        try {
            def msgParams = [params.username]
            def user = ShiroUser.findByUsername(params.username)
            if (user) {
                def token = new Sha1Hash(new Date().toString()).toHex()
                user.passwordReset = token
                if (!user.hasErrors() && user.save()) {
                    emailService.sendPasswordReset(user.username,token)
                    flash.message = "passwd.reset.success"
                    flash.args = msgParams
                    flash.isError = false
                } else {
                    log.warn "Could not save password reset token for user ${params.username}"
                    flash.message = "passwd.reset.internal.error"
                }
            } else {
                log.trace "User ${params.username} could not be found"
                flash.message = "passwd.reset.failure"
                flash.args = msgParams
            }
        } catch (error) {
            log.error "Password reset failure for user '${params.username}'.", error
            flash.message = "passwd.reset.failure"
        }
        redirect(action: "resetPassword")
    }

    def resetPassword = {
        render(view: 'password-reset')
    }

    def doPasswordReset = {
        def user = ShiroUser.findByPasswordReset(params.id)
        if (!user) {
            log.warn "Password reset requested from ${params.id} resulted in user not found"
            flash.message = "passwd.reset.failure"
            flash.args = ['Unknown']
            redirect(controller: 'manageSite', action: 'error')
        } else {
            render(view: 'changePassword', model:[user:user])
        }
    }

    def changePassword = {
        def user = ShiroUser.findByPasswordReset(params.reset)
        if (user) {
            if (params.password.equals(params.password2)) {
                try {
                    user.passwordHash = new Sha1Hash(params.password).toHex()
                    if (!user.hasErrors() && user.save()) {
                        try {
                            user.passwordReset = null
                            user.save()
                        } catch(error) {
                            log.warn "Unable to nullify password reset field for user ${user.username}", error
                        }
                        log.info "User ${user.username}  has successfully changed password."
                        flash.message = "passwd.change.success"
                        redirect(controller: 'manageSite', action: 'info')
                    } else {
                        flash.message = "login.failed"
                        redirect(action: "doPasswordReset", id: user.passwordReset)
                    }
                } catch (Exception e) {
                    log.error "Failed to change user's password", e
                    flash.message = "login.failed"
                    // Now redirect back to the login page.
                    redirect(action: "doPasswordReset", id: user.passwordReset)
                }
            } else {
                log.info "${user}'s new passwords do not match."
                flash.message = "register.passwords.match.failure"
                // Now redirect back to the login page.
                redirect(action: "doPasswordReset", id: user.passwordReset)
            }
        } else {
            flash.message = "passwd.change.failure"
            redirect(controller: 'manageSite', action: 'error')
        }
    }
}
