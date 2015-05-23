package io.github.romanwozniak.banking

import akka.actor.Actor.Receive
import akka.actor._
import akka.actor.Status.Failure
import akka.pattern.ask
import dispatch.Http
import scala.concurrent.duration._

import io.github.romanwozniak.banking.actors.AkkaSystem._
import io.github.romanwozniak.banking.actors.messages._
import io.github.romanwozniak.banking.models.{Currency, EUR, USD, UAH}
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 01:18
 */
object Application2 extends App {

  val system = ActorSystem("default")

  val ratesClient = system.actorOf(Props(new Actor with ActorLogging {

    val msgId = 1
    val path = "akka.tcp://ExchangeRates@127.0.0.1:3000/user/exchangeRates"

    context.actorSelection(path) ! Identify(msgId)

    override def receive = {
      case ActorIdentity(`msgId`, Some(ratesServer)) =>
        log.info("Found remote exchange rates server")
        context.become(active(ratesServer))

      case ActorIdentity(`msgId`, None) =>
        log.warning("Failed to identify remote exchange rates server")
        context.actorSelection(path) ! Identify(msgId)

      case _ => log.warning("Not ready")

    }

    def active(ratesServer: ActorRef): Receive = {
      case msg =>
        val originalSender = sender
        ratesServer.tell(msg, originalSender)
    }

  }))

  system.scheduler.schedule(0 seconds, 5 seconds) {
    ratesClient ! s"GetExchangeRate ${Currency.random.code}/${Currency.random.code}"
  }

  ratesClient ! "hello"

  system.scheduler.scheduleOnce(10 seconds) {
    ratesClient ! "hi"
  }

}
