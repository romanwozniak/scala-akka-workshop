package io.github.romanwozniak.exchangerates.actors

import akka.actor.{Props, Actor, ActorLogging}
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
object ExchangeRatesActor {

  def props(
       yahooService: (Currency, Currency) => Future[String],
       nbuService: (Currency, Currency) => Future[String]) = Props(classOf[ExchangeRatesActor], yahooService, nbuService)

}

class ExchangeRatesActor(
        yahooService: (Currency, Currency) => Future[String],
        nbuService: (Currency, Currency) => Future[String]) extends Actor with ActorLogging {

  private val exchange = """GetExchangeRate ([A-Z]{3})/([A-Z]{3})""".r

  def receive = yahooRates

  def yahooRates: Receive = rates(yahooService)

  def nbuRates: Receive = rates(nbuService)

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
