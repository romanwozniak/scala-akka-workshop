package io.github.romanwozniak.exchangerates.utils

import com.typesafe.config.ConfigFactory
import dispatch._, Defaults._
import io.github.romanwozniak.banking.models.Currency

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 14:42
 */
object YahooExchangeRates {

  private val apiUrl = ConfigFactory.load().getString("exchangeRates.yahoo.url")

  def getRates(from: Currency, to: Currency) = {
    val req = url(apiUrl)
      .addQueryParameter("q", s"select * from yahoo.finance.xchange where pair = '$from$to'")
      .addQueryParameter("env", "store://datatables.org/alltableswithkeys")

    Http(req OK as.xml.Elem) map {rates =>
        val rate = ((rates \ "results").head \ "rate" \ "Rate").text.toDouble
        s"$from/$to => $rate"
    }
  }

}
