import org.apache.shiro.crypto.hash.Sha1Hash
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.media.Image

class BootStrap {
     def imageService

     def init = { servletContext ->

        new File("/home/leanne/Desktop/load").eachFile{file ->
            println ("Reading ${file}")
            def name = file.name.toLowerCase()
            if(name.endsWith(".jpg" ) || name.endsWith(".png")){
                def image = new Image()
                def type = name.endsWith(".jpg" ) ? "JPEG" : "PNG";
                def imgName = name.substring(0, name.length()-4)
                imgName = imgName.replace("[-_]", " ")
                image.name = imgName
                image.image = imageService.read(file.getAbsolutePath(), type)
                image.thumbnail = imageService.thumbnail(image.image)
                image.mimeType = name.endsWith(".jpg" ) ? 'image/jpg' : 'image/png';
                image.save()
                println "Image ${image.name} of type ${image.mimeType} has been saved."
            }
        }

        def adminRole = new ShiroRole(name: "Admin")
        adminRole.addToPermissions("*:*")
        adminRole.save()
        def webAdminRole = new ShiroRole(name: "Administrator")
        webAdminRole.addToPermissions("manageSite:*")
        webAdminRole.save()
        def contentAdminRole = new ShiroRole(name: "Editor")
        contentAdminRole.addToPermissions("article:*")
        contentAdminRole.addToPermissions("manageSite:home")
        contentAdminRole.save()
        def authRole = new ShiroRole(name: "Author")
        authRole.addToPermissions("article:*")
        authRole.addToPermissions("image:*")
        authRole.addToPermissions("manageSite:home")
        authRole.save()
	    def eventRole = new ShiroRole(name: "Event Organiser")
        eventRole.addToPermissions("manageSite:home")
    	eventRole.addToPermissions("event:*")
    	eventRole.save()
    	def venueRole = new ShiroRole(name: "Venue Manager")
        venueRole.addToPermissions("manageSite:home")
    	venueRole.addToPermissions("room:*")
    	venueRole.addToPermissions("venue:*")
    	venueRole.save()

        def theadmin = new ShiroUser(username: "admin", passwordHash: new Sha1Hash("change!t").toHex())
        theadmin.addToRoles(adminRole)
        theadmin.save()
        def user = new ShiroUser(username: "web-admin", passwordHash: new Sha1Hash("change!t").toHex())
        user.addToRoles(webAdminRole)
        user.save()
    	def manager = new ShiroUser(username: "manager", passwordHash: new Sha1Hash("change!t").toHex())
    	manager.addToRoles(venueRole)
    	manager.save()
        def admin = new ShiroUser(username: "editor1", passwordHash: new Sha1Hash("change!t").toHex())
        admin.addToRoles(contentAdminRole)
        admin.save()
        def author1 = new ShiroUser(username: "author1", passwordHash: new Sha1Hash("change!t").toHex())
        author1.addToRoles(authRole)
        author1.save()
        def author2 = new ShiroUser(username: "author2", passwordHash: new Sha1Hash("change!t").toHex())
        author2.addToRoles(authRole)
        author2.save()
        def admin2 = new ShiroUser(username: "editor2", passwordHash: new Sha1Hash("change!t").toHex())
        admin2.addToRoles(authRole)
        admin2.addToRoles(contentAdminRole)
        admin2.save()

        def loremIpsum = """p. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer in tortor nisi. Aliquam lobortis, est id faucibus dictum, erat tortor volutpat lorem, id malesuada nunc turpis quis turpis. Morbi at ultrices mauris. Phasellus odio nisl, convallis a porta eu, tincidunt vel mi. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Morbi a erat eu leo elementum porttitor. Donec elementum justo a ante sagittis feugiat. Vivamus laoreet ipsum lacus, quis blandit elit. Nunc imperdiet, dui malesuada rhoncus iaculis, nibh felis accumsan orci, et tincidunt nisl augue non lectus. Proin a ipsum risus. Vivamus sapien neque, commodo volutpat sagittis in, fermentum non velit.

p. Duis et diam augue. Sed ac augue eget tortor pretium euismod nec et dui. Donec porta nulla et elit eleifend in aliquet lorem ultrices. Cras faucibus malesuada dui, eu mollis lorem scelerisque eu. Phasellus tincidunt consectetur ultricies. Aenean mattis tortor ut lectus scelerisque eu pharetra augue scelerisque. Duis iaculis porta faucibus. Aenean vehicula, dolor eu rhoncus pulvinar, metus mauris posuere ipsum, sit amet vehicula lacus nunc et justo. Duis a ultricies erat. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eu dolor tortor, at pretium sapien. Nulla fermentum, nibh vel rutrum porta, justo tellus mollis diam, at bibendum turpis ante id erat. Suspendisse tincidunt pellentesque mi id tristique. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.

p. Vestibulum viverra convallis mauris non dapibus. Sed ut magna augue, vel pellentesque ipsum. Maecenas varius mi sed sem mattis non elementum purus elementum. Aenean nisl metus, vestibulum ac blandit ut, scelerisque at est. Ut faucibus iaculis velit ac ultricies. In lacinia mauris non neque dictum sit amet semper eros laoreet. Donec rutrum nisl sit amet turpis interdum eu ullamcorper ligula viverra. Aliquam erat volutpat. Curabitur rutrum odio non metus lobortis suscipit. Cras enim arcu, volutpat et sollicitudin vitae, consequat sit amet ipsum. Nulla condimentum vulputate velit, sit amet malesuada mauris varius eleifend. Sed tortor odio, sodales varius sollicitudin non, scelerisque vel dolor. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Praesent ut neque in lorem sagittis semper quis quis neque.

p. Aenean hendrerit, dolor id pharetra porttitor, turpis risus faucibus nisi, nec ultrices turpis eros sed velit. Nullam eros urna, mollis eu dictum a, pretium quis justo. Vivamus non mauris ac elit malesuada accumsan. Nulla pellentesque velit in libero lobortis vehicula. Praesent felis elit, lobortis id eleifend a, fringilla sed neque. Nullam eu tempor velit. Aenean dolor mauris, semper eu congue id, dapibus nec mauris. Integer et pellentesque sapien. Pellentesque facilisis, augue vel fringilla commodo, nunc justo porta ipsum, quis condimentum lacus urna ac augue. In eleifend consectetur pharetra. Quisque tincidunt placerat augue mattis gravida. Sed dapibus, nunc non sagittis aliquet, odio ligula posuere leo, eu pellentesque dui orci a urna. Cras eu nunc est. Morbi tristique, velit ac imperdiet commodo, mi risus tempor quam, vel sagittis nisl mauris vel lacus. Ut id sem sed sapien pharetra adipiscing. Aliquam quis sodales nunc.

p. Integer pellentesque dui id felis euismod condimentum. Nulla facilisi. Pellentesque rutrum rutrum rutrum. Aliquam sed orci dui, sit amet tempor arcu. Maecenas dignissim, dolor id cursus egestas, risus nibh vestibulum ipsum, vel rhoncus quam dolor sed risus. Maecenas condimentum leo nec lorem pretium hendrerit. Aenean diam dolor, egestas quis lobortis vitae, gravida sit amet neque. Vestibulum facilisis, nisi vitae imperdiet commodo, ante lacus vehicula nunc, ac viverra dui ante nec libero. Donec non eros diam, a laoreet tellus. Nulla et tortor a velit malesuada consequat vel a felis. Mauris ut risus sed libero accumsan egestas ut ac magna. Praesent pulvinar, dui vitae malesuada accumsan, nibh lectus placerat metus, vitae vestibulum felis arcu nec orci. Ut at nisi purus."""

        def loremIpsumSummary = """Integer pellentesque dui id felis euismod condimentum. Nulla facilisi. Pellentesque rutrum rutrum rutrum. Aliquam sed orci dui, sit amet tempor arcu. Maecenas dignissim, dolor id cursus egestas, risus nibh vestibulum ipsum, vel rhoncus quam dolor sed risus. Maecenas condimentum leo nec lorem pretium hendrerit. Aenean diam dolor, egestas quis lobortis vitae, gravida sit amet neque. Vestibulum facilisis, nisi vitae imperdiet commodo, ante lacus vehicula nunc, ac viverra dui ante nec libero. Donec non eros diam, a laoreet tellus. Nulla et tortor a velit malesuada consequat vel a felis. Mauris ut risus sed libero accumsan egestas ut ac magna. Praesent pulvinar, dui vitae malesuada accumsan, nibh lectus placerat metus, vitae vestibulum felis arcu nec orci. Ut at nisi purus."""
        def pubArticle1 = new Article(title: "Lorem ipsum dolor sit amet", content: loremIpsum, summary: loremIpsumSummary)
        pubArticle1.author = author1
        pubArticle1.publishState = "Published"
        pubArticle1.deleted = Boolean.FALSE
        pubArticle1.save()

        loremIpsumSummary = """Integer pellentesque dui id felis euismod condimentum. Nulla facilisi. Pellentesque rutrum rutrum rutrum. Aliquam sed orci dui, sit amet tempor arcu. Maecenas dignissim, dolor id cursus egestas, risus nibh vestibulum ipsum, vel rhoncus quam dolor sed risus. Maecenas condimentum leo nec lorem pretium hendrerit. """
        (1..5).each {
            def a = new Article(title: "Article " + it, content: loremIpsum, summary: loremIpsumSummary)
            a.author = author1
            a.publishState = "Unpublished"
            a.deleted = Boolean.FALSE
            a.save()
        }

        (1..1).each {
            def a = new Article(title: "Front Meditation Article " + it, content: loremIpsum, summary: loremIpsumSummary)
            a.author = author1
            a.publishState = "Published"
            a.deleted = Boolean.FALSE
            a.save()
            a.parseTags("Meditation", "front")
        }

        (1..1).each {
            def a = new Article(title: "Other Meditation Article " + it, content: loremIpsum, summary: loremIpsumSummary)
            a.author = author1
            a.publishState = "Published"
            a.deleted = Boolean.FALSE
            a.save()
            a.parseTags("Meditation")
        }
        (1..3).each {
            def a = new Article(title: "Meditation Advice Article " + it, content: loremIpsum, summary: loremIpsumSummary)
            a.author = author1
            a.publishState = "Published"
            a.deleted = Boolean.FALSE
            a.save()
            a.parseTags("Meditation Advice")
        }
        (1..2).each {
            def a = new Article(title: "Meditation Benefits Article " + it, content: loremIpsum, summary: loremIpsumSummary)
            a.author = author1
            a.publishState = "Published"
            a.deleted = Boolean.FALSE
            a.save()
            a.parseTags("Meditation Benefits")
        }
        (1..8).each {
            def a = new Article(title: "News Article " + it, content: loremIpsum, summary: loremIpsumSummary)
            a.author = author1
            a.publishState = "Published"
            a.deleted = Boolean.FALSE
            a.save()
            a.parseTags("News")
        }
        (1..3).each {
            def a = new Article(title: "Front News Article " + it, content: loremIpsum, summary: loremIpsumSummary)
            a.author = author1
            a.publishState = "Published"
            a.deleted = Boolean.FALSE
            a.save()
            a.parseTags("News", "Front")
        }
        (1..1).each {
            def a = new Article(title: "Front Community Article " + it, content: loremIpsum, summary: loremIpsumSummary)
            a.author = author1
            a.publishState = "Published"
            a.deleted = Boolean.FALSE
            a.save()
            a.parseTags("Community", "Front")
        }
        (1..2).each {
            def a = new Article(title: "Front Well Being Article " + it, content: loremIpsum, summary: loremIpsumSummary)
            a.author = author1
            a.publishState = "Published"
            a.deleted = Boolean.FALSE
            a.save()
            a.parseTags("Wellbeing", "Front")
        }
        (1..3).each {
            def a = new Article(title: "Front Buddhism Article " + it, content: loremIpsum, summary: loremIpsumSummary)
            a.author = author1
            a.publishState = "Published"
            a.deleted = Boolean.FALSE
            a.save()
            a.parseTags("Buddhism", "Front")
        }
        def na1 = Article.findByTitle("News Article 1")
        na1.image = Image.findByName("buddha")
        na1.save()

        def na2 = Article.findByTitle("News Article 4")
        na2.image = Image.findByName("flowers")
        na2.save()

        def ma2 = Article.findByTitle("Meditation Advice Article 3")
        ma2.image = Image.findByName("dragon")
        ma2.save()

        def mb2 = Article.findByTitle("Meditation Benefits Article 2")
        mb2.image = Image.findByName("deity")
        mb2.save()

        def a = new Article(title: "Image Article", content: "Hello, can you see this? [deity,left,50px](image)", summary: loremIpsumSummary)
        a.author = author1
        a.publishState = "Published"
        a.deleted = Boolean.FALSE
        a.save()
        a.parseTags("News")

        def markup = """h1. Heading 1
h2. Heading 2

h3. Heading 3

h4. Heading 4

h5. Heading 5

h6. Heading 6

p. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer in tortor nisi. Aliquam lobortis, est id faucibus dictum, erat tortor volutpat lorem, id malesuada nunc turpis quis turpis. Morbi at ultrices mauris. Phasellus odio nisl, convallis a porta eu, tincidunt vel mi. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Morbi a erat eu leo elementum porttitor. Donec elementum justo a ante sagittis feugiat. Vivamus laoreet ipsum lacus, quis blandit elit. Nunc imperdiet, dui malesuada rhoncus iaculis, nibh felis accumsan orci, et tincidunt nisl augue non lectus. Proin a ipsum risus. Vivamus sapien neque, commodo volutpat sagittis in, fermentum non velit.

bq. Duis et diam augue. (See footnote[1]) Sed ac augue eget tortor pretium euismod nec et dui. Donec porta nulla et elit eleifend in aliquet lorem ultrices. Cras faucibus malesuada dui, eu mollis lorem scelerisque eu. Phasellus tincidunt consectetur ultricies. Aenean mattis tortor ut lectus scelerisque eu pharetra augue scelerisque. Duis iaculis porta faucibus. Aenean vehicula, dolor eu rhoncus pulvinar, metus mauris posuere ipsum, sit amet vehicula lacus nunc et justo. Duis a ultricies erat. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eu dolor tortor, at pretium sapien. Nulla fermentum, nibh vel rutrum porta, justo tellus mollis diam, at bibendum turpis ante id erat. Suspendisse tincidunt pellentesque mi id tristique. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.

fn1. This is a footnote.

# red
# green
# favorite
## pink
## lavender
# blue


* red
* green
* favorite
** pink
** lavender
* blue

Text styles:
_emphasis_
*strong*
__italic__
**bold**
+inserted text+
x^2^
10~2~.
Jane said, "Hello mark" and Mark replied, 'Hello, Nice day isn't it?'.
Checking the apostrophe with it's.
And how about the & aka ampersand character as well?
em -- dash
en - dash
foo(tm)
foo(r)
foo(c)

Alignment tests:
> right
< left
= center
<> justified Duis et diam augue. Sed ac augue eget tortor pretium euismod nec et dui. Donec porta nulla et elit eleifend in aliquet lorem ultrices. Cras faucibus malesuada dui, eu mollis lorem scelerisque eu. Phasellus tincidunt consectetur ultricies. Aenean mattis tortor ut lectus scelerisque eu pharetra augue scelerisque. Duis iaculis porta faucibus. Aenean vehicula, dolor eu rhoncus pulvinar, metus mauris posuere ipsum, sit amet vehicula lacus nunc et justo. Duis a ultricies erat. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eu dolor tortor, at pretium sapien. Nulla fermentum, nibh vel rutrum porta, justo tellus mollis diam, at bibendum turpis ante id erat. Suspendisse tincidunt pellentesque mi id tristique. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.

Tables:
|_. a|_. table|_. header|
|a|table|row|
|a|table|row|

Moving onto acronyms hover mouse over:
    AKA(Also Known As)
"""
        def pubArticle2 = new Article(title: "Typesetting Test", content: markup, summary: "Useful for checking both textile mark up and style sheets")
        pubArticle2.author = author2
        pubArticle2.publishState = "Published"
        pubArticle2.deleted = Boolean.FALSE
        pubArticle2.save()

        def tibetanText = """p(tibetan). %(white)ༀ%་ %(green)མ% %(yellow)ནི%་ %(cyan)པ% %(red)དྨེ%་ %(blue)ཧཱུྃ%

p. With a link to tibet.net:

p(tibetan). "ཨབཅ":http://tibet.net/en/index.php"""
        def pubArticle3 = new Article(title: "Tibetan HTML Test", content: tibetanText, summary: "Testing if textile encoder & database can cope with Tibetan unicode. Will only display properly in Safari browsers I think.")
        pubArticle3.author = author1
        pubArticle3.publishState = "Published"
        pubArticle3.deleted = Boolean.FALSE
        pubArticle3.save()
     }

     def destroy = {
     }

}
