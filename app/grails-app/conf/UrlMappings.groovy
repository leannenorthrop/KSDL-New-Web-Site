class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
          constraints {
             // apply constraints here
          }
      }
      "/"(controller:"home")
      "500"(view:"home/internalError")
      "404"(view:"home/notFound")
	  "401"(view:"home/unauthorised")
	  "403"(view:"home/unauthorised")	
	  "503"(view:"home/unauthorised")
    }
}
