package me.mig.cxb

import com.typesafe.config.ConfigFactory
import me.mig.cxb.actor.ActorNames
import me.mig.cxb.web.TestAPI
import me.mig.fission.DAO
import org.slf4j.LoggerFactory

object Main extends App {
  Startup.Init()
}

object Startup {

  private lazy val log = LoggerFactory.getLogger(this.getClass)

  def Init() = {

    val config = ConfigFactory.load()


    DAO.init()



    val node = CxNode(ActorNames.localSystem, config).asInstanceOf[CxNode]
    node.Init()




    TestAPI.Init(node.actorSystem)


  }

}
