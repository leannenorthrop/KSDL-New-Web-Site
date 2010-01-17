import org.apache.shiro.crypto.hash.Sha1Hash
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.media.Image
import org.samye.dzong.london.ShiroUser
import org.samye.dzong.london.ShiroRole
class BootStrap {
     def imageService
     def greenMail

     def init = { servletContext ->
         environments {
             development {
                 greenMail.start()
             }
             test {
                 greenMail.start()
             }
         }
    }

    def destroy = {
    }

}
