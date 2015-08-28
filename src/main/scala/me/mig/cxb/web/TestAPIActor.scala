package me.mig.cxb.web

import akka.actor.{Actor, Props}
import akka.io.IO
import me.mig.cxb.actor.{ActorNames, SubSystem}
import spray.can.Http

object TestAPI extends SubSystem {

  override def start() = {
    val service = system.actorOf(Props[TestAPIActor], ActorNames.testAPI)
    val interface:String =  "localhost"
    val port:Int = 8080

    IO(Http) ! Http.Bind(service, interface, port)
  }

}

class TestAPIActor extends Actor with TestCxService {
  def receive: Receive = runRoute(routes)
  def actorRefFactory = context
}
