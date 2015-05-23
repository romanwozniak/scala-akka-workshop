package io.github.romanwozniak.banking

import akka.actor.Actor.Receive
import akka.actor._
import akka.actor.Status.Failure
import akka.pattern.ask
import com.typesafe.config.ConfigFactory
import dispatch.Http
import io.github.romanwozniak.banking.actors.ExchangeRateLookupActor
import io.github.romanwozniak.banking.monitoring.KamonMetrics
import scala.concurrent.duration._

import io.github.romanwozniak.banking.actors.AkkaSystem._
import io.github.romanwozniak.banking.actors.messages._
import io.github.romanwozniak.banking.models.{Currency, EUR, USD, UAH}
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 01:18
 */
object RemoteExchangeRate extends App with KamonMetrics {

  val system = ActorSystem("BankingSystem")


  val path = ConfigFactory.load().getString("exchangeRate.path")
  val ratesClient = system.actorOf(Props(classOf[ExchangeRateLookupActor], path), "ratesClientLookUp")

  system.scheduler.schedule(0 seconds, 5 seconds) {
    (ratesClient ? s"GetExchangeRate ${Currency.random.code}/${Currency.random.code}")(5 seconds) map {
      case resp: String => println(resp)
    }
  }

}
