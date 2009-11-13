package org.samye.dzong.london

class Profile {

    static hasMany = [roles : Role]

    //static belongsTo = ShiroUser
    static constraints = {
        roles(nullable:true)
    }
}
