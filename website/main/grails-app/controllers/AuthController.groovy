import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.web.SavedRequest
import org.apache.shiro.web.WebUtils
import org.apache.shiro.crypto.hash.Sha1Hash

class AuthController {
    def shiroSecurityManager

    def index = { redirect(action: "login", params: params) }

    def login = {
        return [ username: params.username, rememberMe: (params.rememberMe != null), targetUri: params.targetUri ]
    }

    def register = {
        if (params.password.equals(params.password2)) {
            try {
                if (ShiroUser.findByUsername(params.username)) {
                    log.info "Register user passwords do not match."
                    flash.message = message(code: "register.username.in.use")
                    // Now redirect back to the login page.
                    redirect(action: "login", params: params)
                } else {
                    def newUser = new ShiroUser(username: params.username, passwordHash: new Sha1Hash(params.password).toHex())
                    newUser.save()
                    log.info "Registered new user " + params.username
                    def authToken = new UsernamePasswordToken(params.username, params.password)
                    def targetUri = "/"
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

                        // Now redirect back to the login page.
                        redirect(action: "login", params: params)
                    }
                }
            } catch (Exception e) {
                log.error "Failed to register new user", e
                flash.message = message(code: "login.failed")
                // Now redirect back to the login page.
                redirect(action: "login", params: params)
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
        render "You do not have permission to access this page."
    }
}
