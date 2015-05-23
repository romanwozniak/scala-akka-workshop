package io.github.romanwozniak.exchangerates

import akka.actor.{Props, ActorSystem}
import akka.routing.ScatterGatherFirstCompletedPool
import io.github.romanwozniak.exchangerates.actors.ExchangeRatesActor
import io.github.romanwozniak.exchangerates.utils.{NBUExchangeRates, YahooExchangeRates}
import scala.concurrent.duration._

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 11:00
 */
object RemoteExchangeRatesApplication extends App {

  lazy val exchangeRatesSystem = ActorSystem("ExchangeRates")

  exchangeRatesSystem.actorOf(
    ExchangeRatesActor.props(YahooExchangeRates.getRates, NBUExchangeRates.getRates)
      .withRouter(ScatterGatherFirstCompletedPool(10, within = 10 seconds)),
    "exchangeRates")

}
