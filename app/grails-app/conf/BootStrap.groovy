import org.apache.shiro.crypto.hash.Sha1Hash
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.media.Image
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
		
        def dataDir = (filedir.endsWith('/') ? filedir : filedir + '/' )+ "data"
        configObject.moonData = new Expando()
        ['full_moon.txt':'fullMoon','new_moon.txt':'newMoon','last_quarter_moon.txt':'lastQuarter','first_quarter_moon.txt':'firstQuarter'].each { filename,name ->
            def list = [:]
            new File(dataDir, filename).eachLine { date ->
                try { 
                    def month = date[0..3].trim()
                    def day = date[4..6].trim()
                    def year = date[12..-1].trim()
                    def thedate = new Date().parse("dd/MMM/yyyy","${day}/${month}/${year}")
                    list.put(thedate.format('yyyy-MM-dd'), thedate)
                } catch(error) {
                    log.warn "Could not parse ${date} from ${filename}",error
                }
            }
            configObject.moonData[name] = list
        }


         environments {
             development {
                 try {
                     greenMail = new GreenMail(ServerSetupTest.ALL)
                     greenMail.start();
                     servletContext.setAttribute("greenmail", greenMail)
                 } catch(errors) {
                     log.warn "Could not start greenmail", errors
                 }
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
                def gm = servletContext.getAttribute("greenmail")
                if (gm) {
                    try {
                        gm.stop();
                        servletContext.setAttribute("greenmail", null)
                    }catch(ignore){}
                } else {
                    greenMail.stop();
                }

                greenMail = null
            }
            test {
                def gm = servletContext.getAttribute("greenmail")
                if (gm) {
                    try {
                        gm.stop();
                        servletContext.setAttribute("greenmail", null)
                    }catch(ignore){}
                } else {
                    greenMail.stop();
                }
                def config = ConfigurationHolder.getConfig()
                config.greenmail = null 
                greenMail = null
            }
        }
    }

}
