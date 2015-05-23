package io.github.romanwozniak.exchangerates.actors

import akka.actor.{Actor, ActorLogging}
import akka.pattern.pipe
import dispatch._, Defaults._
import io.github.romanwozniak.banking.actors.messages.GetExchangeRate
import io.github.romanwozniak.banking.models.Currency
import io.github.romanwozniak.exchangerates.utils.{NBUExchangeRates, YahooExchangeRates}
import io.github.romanwozniak.exchangerates.actors.messages._


/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 00:51
 */
class ExchangeRatesActor extends Actor with ActorLogging {

  private val exchange = """GetExchangeRate ([A-Z]{3})/([A-Z]{3})""".r

  def receive = yahooRates

  def yahooRates: Receive = rates(YahooExchangeRates.getRates)

  def nbuRates: Receive = rates(NBUExchangeRates.getRates)



  def rates(getRates: (Currency, Currency) => Future[String]): Receive = {
    case GetExchangeRate(from, to) =>
      getRates(from, to) pipeTo sender()

    case msg: String => msg match {
      case exchange(from, to) =>
        getRates(Currency(from), Currency(to)) pipeTo sender()

      case msg => log.info(s"Received unknown message: $msg")
    }

    case BecomeYahooConverter => context.become(yahooRates)
    case BecomeNBUConverter   => context.become(nbuRates)

    case msg => log.info(s"Received unknown message: $msg")
  }

}
