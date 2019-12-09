package com.example

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route

import scala.concurrent.Future

import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.AskPattern._
import akka.util.Timeout
import com.example.Models.User
import com.example.Services.UserService

//#import-json-formats
//#user-routes-class
class UserRoutes()(implicit val system: ActorSystem[_], userService: UserService) {

  //#user-routes-class
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import JsonFormats._
  //#import-json-formats

  // If ask takes more time than this to complete the request is failed
  private implicit val timeout = Timeout.create(system.settings.config.getDuration("my-app.routes.ask-timeout"))

  val userRoutes: Route =
    pathPrefix("users") {
      pathEnd {
        post {
          entity(as[User]) { user =>
            userService.sendUserToQueue(user)
            complete((StatusCodes.OK, user))
          }
        }
      }
    }

}
