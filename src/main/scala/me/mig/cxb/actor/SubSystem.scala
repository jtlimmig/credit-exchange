package me.mig.cxb.actor

import akka.actor.ActorSystem

trait SubSystem {
  protected implicit var system: ActorSystem = null
  def Init(s: ActorSystem): Unit = {
    system = s
    start()
  }
  protected def start(): Unit
}
