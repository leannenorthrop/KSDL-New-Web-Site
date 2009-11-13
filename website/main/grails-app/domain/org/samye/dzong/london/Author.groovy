package org.samye.dzong.london


class Author extends Person {
    String aka;
    String interests;
    String aboutMe;
    String personalHomePage;

    static constraints = {
        interests(maxSize:1024)
        aboutMe(maxSize:5000)
        personalHomePage(url:true)
    }

    String toString() {
        return "${aka} (${firstName} ${lastName})"
    }
}
