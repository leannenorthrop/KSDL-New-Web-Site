import org.apache.shiro.crypto.hash.Sha1Hash
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.media.Image
import org.samye.dzong.london.ShiroUser
import org.samye.dzong.london.ShiroRole
import com.icegreen.greenmail.util.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import java.util.zip.*;

class BootStrap {
     def imageService
     def greenMail

     def init = { servletContext ->
		def configObject = ConfigurationHolder.getConfig()	
		def filedir = servletContext.getRealPath('/')
		configObject.fileuploader.attachments.path = filedir + "files"
		println "****" + configObject.fileuploader.attachments.path
						
         File.metaClass.unzip = { String dest ->
          //in metaclass added methods, 'delegate' is the object on which
          //the method is called. Here it's the file to unzip
          def result = new ZipInputStream(new FileInputStream(delegate))
          def destFile = new File(dest)
          if(!destFile.exists()){
            destFile.mkdir();
          }
          result.withStream{
            def entry
            while(entry = result.nextEntry){
              if (!entry.isDirectory()){
                new File(dest + File.separator + entry.name).parentFile?.mkdirs()
                def output = new FileOutputStream(dest + File.separator
                                                  + entry.name)
                output.withStream{
                  int len = 0;
                  byte[] buffer = new byte[4096]
                  while ((len = result.read(buffer)) > 0){
                    output.write(buffer, 0, len);
                  }
                }
             }
             else {
               new File(dest + File.separator + entry.name).mkdir()
             }
            }
          }
         }


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
             production {
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
