package org.samye.dzong.london.site

class AdminController {

    def index = {
        redirect(action: "home")
    }

    def home = {
        return render(view:'home',model:[])
    }
}
