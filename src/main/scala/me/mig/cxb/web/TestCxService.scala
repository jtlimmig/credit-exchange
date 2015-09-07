package me.mig.cxb.web

import me.mig.cxb.web.auth.ResourceAuthenticator
import spray.http.MediaTypes._
import spray.json.DefaultJsonProtocol
import spray.routing.HttpService

import scala.concurrent.ExecutionContext.Implicits.global

case class TestModel(name:String)

object TestJsonFormat extends DefaultJsonProtocol {
  implicit val TestFormat = jsonFormat1(TestModel)
}

trait TestCxService extends HttpService with ResourceAuthenticator {

  import TestJsonFormat._
  import spray.httpx.SprayJsonSupport.sprayJsonMarshaller
  import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller

  def routes = root

  def root = authenticate(tokenAuthenticator) { auth =>
    path("") {
      authorize(allowedScopes(auth, "test-scope")) {

        complete(s"OK user: ${auth.userId} client: ${auth.clientId}")
      }
    } ~ path("json") {
      authorize(allowedScopes(auth, "test-scope")) {
        respondWithMediaType(`application/json`) {
          complete( TestModel("chris") )
        }
      }
    } ~ path("test" / Segment) { x=>
      authorize(allowedScopes(auth, "test-scope")) {
        respondWithMediaType(`application/json`) {

          // curl -H "Authorization: Bearer 852fe10712f44658b5d3598e03f24ce8981911189fb547ed9ce30260070a6026" localhost:8080/test/1234
          get {
            complete( TestModel("chris " + x) )
          }

          // curl -H "Authorization: Bearer 852fe10712f44658b5d3598e03f24ce8981911189fb547ed9ce30260070a6026" -H "Content-Type: application/json" -X POST -d '{"name":"lol"}' localhost:8080/test/1234
          post {
            entity(as[TestModel]) { m =>
              complete( m )
            }
          }
        }
      }
    }
  }
}

