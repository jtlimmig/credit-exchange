package me.mig.cxb

import me.mig.cxb.actor.ActorNames
import me.mig.cxb.web.TestAPI
import me.mig.fission.{Configuration, DAO}
import org.slf4j.LoggerFactory

object Main extends App {
  Startup.Init()
}

object Startup {

  private lazy val log = LoggerFactory.getLogger(this.getClass)

  def Init() = {

    Configuration.init()

    DAO.init()



    val node = CxNode(ActorNames.localSystem, Configuration.get).asInstanceOf[CxNode]
    node.Init()




    TestAPI.Init(node.actorSystem)


  }

}
