logLevel := Level.Warn

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")

//https://github.com/rtimush/sbt-updates
//sbt dependencyUpdates
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.1.8")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.8.5")
