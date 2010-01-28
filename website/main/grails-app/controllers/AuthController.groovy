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
        if (ShiroUser.findByUsername(params.username)) {
            log.info "Username ${params.username} is in use."
            flash.message = message(code: "register.username.in.use")
            // Now redirect back to the login page.
            redirect(action: "login", params: params)
        } else if (params.password.equals(params.password2)) {
            try {
                def newUser = new ShiroUser(username: params.username, passwordHash: new Sha1Hash(params.password).toHex())
                newUser.save()
                def token = new Sha1Hash(new Date().toString()).toHex()
                newUser.passwordReset = token
                if (!newUser.hasErrors() && newUser.save()) {
                    emailService.sendAccountVerification(newUser.username,token)
                    flash.message = "You have successfully created an account. An email has been sent your account which explains how to request particular access rights."
                    render(view: 'changedPassword', model:[user:newUser])
                } else {
                    flash.message = "You have successfully created an account but there was an internal error and your account verification can not be sent to your email account. Please email amdin@lsd.org for assistance."
                    render(view: 'error')
                }
            } catch (Exception e) {
                log.error "Sorry, but we were unable to create a new account for you. Please try again.", e
                flash.message = message(code: "login.failed")
                // Now redirect back to the login page.
                render(view: "error")
            }
        } else {
            log.info "Register user passwords do not match."
            flash.message = message(code: "register.passwords.match.failure")
            // Now redirect back to the login page.
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
        flash.message = "You do not have permission to access this page."
        render(view: 'error', model:[])
    }

    def onResetPassword = {
        try {
            def msgParams = [params.username].toArray()
            def user = ShiroUser.findByUsername(params.username)
            if (user) {
                def token = new Sha1Hash(new Date().toString()).toHex()
                user.passwordReset = token
                if (!user.hasErrors() && user.save()) {
                    emailService.sendPasswordReset(user.username,token)
                    flash.message = messageSource.getMessage("passwd.reset.success", msgParams, null)
                } else {
                    flash.message = "Sorry, there was an internal error and your password can not be reset. Please try again. If you require assistance please email amdin@lsd.org"
                }
            } else {
                flash.message = messageSource.getMessage("passwd.reset.failure", msgParams, null)
            }
        } catch (error) {
            log.error "Password reset failure for user '${params.username}'.", error
            flash.message = "Sorry, there was an internal error and your password can not be reset. Please try again. If you require assistance please email amdin@lsd.org"
        }
        redirect(action: "resetPassword")
    }

    def resetPassword = {
        render(view: 'password-reset', model:[])
    }

    def doPasswordReset = {
        def user = ShiroUser.findByPasswordReset(params.id)
        if (!user) {
            flash.message = messageSource.getMessage("passwd.reset.failure", ['Unknown'].toArray(), null)
            render(view: 'error', model:[])
        } else {
            render(view: 'changePassword', model:[user:user])
        }
    }

    def changePassword = {
        def user = ShiroUser.findByPasswordReset(params.reset)
        if (user) {
            if(params.version) {
                def version = params.version.toLong()
                if(articleInstance.version > version) {
                    articleInstance.errors.rejectValue("version", "article.optimistic.locking.failure", "Another user has updated your details while you were editing.")
                    render(view:'doPasswordReset',id: user.passwordReset)
                }
            } else if (params.password.equals(params.password2)) {
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
                        flash.message = "You have successfully changed your password.<br/> You may now sign in with your new password."
                        render(view: 'changedPassword', model:[user:user])
                    } else {
                        flash.message = message(code: "login.failed")
                        // Now redirect back to the login page.
                        redirect(action: "doPasswordReset", id: user.passwordReset)
                    }
                } catch (Exception e) {
                    log.error "Failed to change user's password", e
                    flash.message = message(code: "login.failed")
                    // Now redirect back to the login page.
                    redirect(action: "doPasswordReset", id: user.passwordReset)
                }
            } else {
                log.info "User's new passwords do not match."
                flash.message = message(code: "register.passwords.match.failure")
                // Now redirect back to the login page.
                redirect(action: "doPasswordReset", id: user.passwordReset)
            }
        } else {
            flash.message = "Sorry, we can not find your details. Please request again for a password reset."
            render(view: 'error', model:[])
        }
    }

    def acl = {
        def user = ShiroUser.findByPasswordReset(params.id)
        if (!user) {
            flash.message = messageSource.getMessage("passwd.reset.failure", ['Unknown'].toArray(), null)
            render(view: 'error', model:[])
        } else {
            render(view: 'roleRequest', model:[user:user])
        }
    }
}
