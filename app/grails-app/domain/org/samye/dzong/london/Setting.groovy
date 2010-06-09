package org.samye.dzong.london

class Setting {
    String name
    String value

    static constraints = {
        name(nullable: false, blank: false, unique:true,inList:['DefaultTheme'])
        value(nullable: false, blank: false)
    }

    static namedQueries = {
        defaultTheme { name ->
            eq 'name', "DefaultTheme"
        }
    }

    String toString() {
        name
    }
}
