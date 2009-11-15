package org.samye.dzong.london

class HelpController {

    def index = {
        render(view:'index',model:[])
    }

    def textile = {
        render(view:'textile-format',model:[])
    }
}
