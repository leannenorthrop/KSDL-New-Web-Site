import org.apache.shiro.crypto.hash.Sha1Hash

class BootStrap {

     def init = { servletContext ->
        def user = new ShiroUser(username: "admin", passwordHash: new Sha1Hash("change!t").toHex())
        user.addToPermissions("*:*")
        user.save()
     }

     def destroy = {
     }
}
