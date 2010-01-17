import org.apache.shiro.crypto.hash.Sha1Hash
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.media.Image
import org.samye.dzong.london.ShiroUser
import org.samye.dzong.london.ShiroRole
import com.icegreen.greenmail.util.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class BootStrap {
     def imageService
     def greenMail

     def init = { servletContext ->
         environments {
             development {
                 greenMail = new GreenMail(ServerSetupTest.ALL)
                 greenMail.start();
                 servletContext.setAttribute("greenmail", greenMail)
             }
             test {
                 greenMail = new GreenMail(ServerSetupTest.ALL)
                 greenMail.start();
                 servletContext.setAttribute("greenmail", greenMail)
                 def config = ConfigurationHolder.getConfig()
                 config.greenmail = greenMail
             }
         }
    }

    def destroy = {
        environments {
            development {
                greenMail.stop();
            }
            test {
                greenMail = new GreenMail();
                greenMail.stop();
            }
        }
    }

}
