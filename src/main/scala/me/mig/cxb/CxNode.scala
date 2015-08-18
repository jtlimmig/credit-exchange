package me.mig.cxb

import akka.actor.{ExtendedActorSystem, ActorSystem, ExtensionId, Extension}
import com.typesafe.config.Config
import me.mig.cxb.actor.ActorNames

class CxNode (val actorSystem: ActorSystem) extends Extension {
  ActorNames.context = actorSystem
}

object CxNode extends ExtensionId[CxNode] {
  def apply(name: String, config: Config) : Extension = CxNode(ActorSystem(name, config))
  override def createExtension(system: ExtendedActorSystem) = new CxNode(system)
}