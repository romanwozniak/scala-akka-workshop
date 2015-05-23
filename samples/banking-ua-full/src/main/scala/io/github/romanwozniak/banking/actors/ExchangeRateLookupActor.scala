package io.github.romanwozniak.banking.actors

import akka.actor._
import scala.concurrent.duration._
/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/24/15, 01:22
 */
class ExchangeRateLookupActor(path: String) extends Actor with ActorLogging {

  val msgId = 1

  import context.dispatcher

  def sendIdentifyRequest() = {
    context.actorSelection(path) ! Identify(msgId)
    context.system.scheduler.scheduleOnce(10 seconds, self, ReceiveTimeout)
  }

  sendIdentifyRequest()

  def receive = {
    case ActorIdentity(`msgId`, Some(ratesServer)) =>
      log.info("Found remote exchange rates server")
      context.watch(ratesServer)
      context.become(active(ratesServer))

    case ActorIdentity(`msgId`, None) =>
      log.warning("Failed to identify remote exchange rates server")

    case ReceiveTimeout => sendIdentifyRequest()

    case _ => log.warning("Not ready")

  }

  def active(ratesServer: ActorRef): Receive = {
    case Terminated(`ratesServer`) =>
      context.actorSelection(path) ! Identify(msgId)
      context.unbecome()

    case ReceiveTimeout =>

    case msg =>
      val originalSender = sender
      ratesServer.tell(msg, originalSender)
  }

}
