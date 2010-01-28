package org.samye.dzong.london

class ManageSiteController {

    def index = {
        redirect(action: "home")
    }

    def home = {
        return render(view:'home')
    }

    def info = {
        return render(view:'info')
    }

    def error = {
        return render(view:'error')
    }
}
