package io.github.romanwozniak.exchangerates

import akka.actor.{Inbox, Props, ActorSystem}
import akka.pattern.ask
import io.github.romanwozniak.banking.actors.messages.GetExchangeRate
import io.github.romanwozniak.banking.models.{Currency, UAH, USD}
import io.github.romanwozniak.exchangerates.actors.ExchangeRatesSupervisor
import io.github.romanwozniak.exchangerates.utils.HttpDependent
import scala.concurrent.duration._
import scala.util.Try

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 18:50
 */
object FaultHandlingApplication extends App with HttpDependent {

  val system = ActorSystem("FaultHandling")

  val exchangeRates = system.actorOf(Props[ExchangeRatesSupervisor], "exchangeRatesSupervisor")

  import system.dispatcher

  system.scheduler.schedule(0 seconds, 5 seconds) {
    (exchangeRates ? GetExchangeRate(Currency.random, Currency.random))(5 seconds) map {
      case resp: String => println(resp)
      case _ =>
    }
  }

}
