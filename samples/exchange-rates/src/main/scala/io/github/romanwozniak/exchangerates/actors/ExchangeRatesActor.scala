package io.github.romanwozniak.exchangerates.actors

import akka.actor.{Actor, ActorLogging}
import akka.pattern.pipe
import com.typesafe.config.ConfigFactory
import dispatch._, Defaults._
import io.github.romanwozniak.banking.actors.messages.{ExchangeRate, GetExchangeRate}

import scala.util.Right

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 00:51
 */
class ExchangeRatesActor extends Actor with ActorLogging {

  private val apiUrl = ConfigFactory.load().getString("exchangeRates.url")

  private val exchange = """GetExchangeRate ([A-Z]{3})/([A-Z]{3})""".r

  def receive = {
    case GetExchangeRate(from, to) =>
      getRates(from.code, to.code) pipeTo sender()


    case msg: String => msg match {
      case exchange(from, to) =>
        getRates(from, to) pipeTo sender()

      case msg => log.info(s"Received unknown message: $msg")
    }


    case msg => log.info(s"Received unknown message: $msg")
  }



  private def getRates(from: String, to: String) = {
    val req = url(apiUrl)
      .addQueryParameter("q", s"select * from yahoo.finance.xchange where pair = '$from$to'")
      .addQueryParameter("env", "store://datatables.org/alltableswithkeys")

    Http(req OK as.xml.Elem).either map {

      case Right(rates) =>
        val rate = ((rates \ "results").head \ "rate" \ "Rate").text.toDouble
        s"$from/$to => $rate"

      case Left(ex) =>
        log.error(ex, "Failed to retrieve exchange rates information: ")
        throw ex

    }
  }

}
