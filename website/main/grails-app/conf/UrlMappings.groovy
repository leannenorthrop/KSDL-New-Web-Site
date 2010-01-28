class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
          constraints {
             // apply constraints here
          }
      }
      "/"(controller:"home")
      "500"(controller:"home", action:"internalError")
      "404"(view:"home/notFound")
    }
}
