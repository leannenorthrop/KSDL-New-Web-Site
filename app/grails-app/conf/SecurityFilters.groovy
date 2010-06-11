/**
 * Generated by the Shiro plugin. This filters class protects all URLs
 * via access control by convention.
 */
class SecurityFilters {
    def filters = {
        all(uri: "/**") {
            before = {
                // Ignore direct views (e.g. the default main index page).
                if (!controllerName) return true
                if (!actionName) return true

                def allowedControllers = ['home', 'help', 'feed', 'news','meditation','community','wellbeing','buddhism','aboutUs']
                def allowedActions = ['view', 'index', 'src', 'thumbnail', 'home', 'requestPermission', 'requestRoles', 'error', 'info','list','all','query','send','calendar','events','event']
                if (allowedControllers.contains(controllerName) || allowedActions.contains(actionName)){
                    return true
                }

                // Access control by convention.
                accessControl()
            }
        }
    }
}
