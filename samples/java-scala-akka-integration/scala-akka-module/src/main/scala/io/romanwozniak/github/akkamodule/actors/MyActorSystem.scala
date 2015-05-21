package io.romanwozniak.github.akkamodule.actors

import akka.actor.{Props, ActorSystem}

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/21/15, 11:05
 */
object MyActorSystem {

  val system = ActorSystem.apply("MySystem")
  val dummyActor = system.actorOf(Props[ConsoleLoggerActor], "dummyActor")

}
