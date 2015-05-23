package io.github.romanwozniak.exchangerates

import akka.actor.{Props, ActorSystem}
import akka.routing.{ScatterGatherFirstCompletedPool, RoundRobinPool}
import io.github.romanwozniak.exchangerates.actors.ExchangeRatesActor
import scala.concurrent.duration._

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 11:00
 */
object RemoteExchangeRatesApplication extends App {

  lazy val exchangeRatesSystem = ActorSystem("ExchangeRates")

  exchangeRatesSystem.actorOf(
    Props[ExchangeRatesActor].withRouter(
      ScatterGatherFirstCompletedPool(10, within = 10 seconds)
    ), "exchangeRates")

}
