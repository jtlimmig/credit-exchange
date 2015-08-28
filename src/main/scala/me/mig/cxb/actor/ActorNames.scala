package me.mig.cxb.actor

import akka.actor.ActorSystem


object ActorNames {

  var context:ActorSystem = null

  val localSystem: String = "cxb"
  val testAPI: String = "testAPI"

}
