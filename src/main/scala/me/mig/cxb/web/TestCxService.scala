package me.mig.cxb.web

import me.mig.cxb.web.auth.ResourceAuthenticator
import spray.routing.HttpService
import scala.concurrent.ExecutionContext.Implicits.global

trait TestCxService extends HttpService with ResourceAuthenticator {
  def routes = root

  def root = authenticate(tokenAuthenticator) { auth =>
    path("") {
      authorize(allowedScopes(auth, "test-scope")) {
        complete(s"The user is ${auth.userId} ")
      }
    }
  }
}

