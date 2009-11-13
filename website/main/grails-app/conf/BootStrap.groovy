import org.apache.shiro.crypto.hash.Sha1Hash

class BootStrap {

     def init = { servletContext ->
        def adminRole = new ShiroRole(name: "Administrator")
        adminRole.addToPermissions("*:*")
        adminRole.save()
        def contentAdminRole = new ShiroRole(name: "Editor")
        contentAdminRole.addToPermissions("article:*")
        contentAdminRole.save()
        def authRole = new ShiroRole(name: "Author")
        authRole.addToPermissions("article:*")
        authRole.save()

        def user = new ShiroUser(username: "admin", passwordHash: new Sha1Hash("change!t").toHex())
        user.addToRoles(adminRole)
        user.save()
        def admin = new ShiroUser(username: "editor1", passwordHash: new Sha1Hash("password").toHex())
        admin.addToRoles(contentAdminRole)
        admin.save()
        def author1 = new ShiroUser(username: "author1", passwordHash: new Sha1Hash("password").toHex())
        author1.addToRoles(authRole)
        author1.save()
        def author2 = new ShiroUser(username: "author2", passwordHash: new Sha1Hash("password").toHex())
        author2.addToRoles(authRole)
        author2.save()
        def admin2 = new ShiroUser(username: "editor2", passwordHash: new Sha1Hash("password").toHex())
        admin2.addToRoles(authRole)
        admin2.addToRoles(contentAdminRole)
        admin2.save()
     }

     def destroy = {
     }
}
