package io.github.romanwozniak.exchangerates.utils

import com.typesafe.config.ConfigFactory
import dispatch._, Defaults._
import io.github.romanwozniak.banking.models.Currency

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 14:57
 */
object NBUExchangeRates extends ExchangeRatesService {

  private val apiUrl = ConfigFactory.load().getString("exchangeRates.privatbank.url")

  def getRates(from: Currency, to: Currency): Future[String] = {
    val req = url(apiUrl)

    Http(req OK as.xml.Elem) map {rates =>
      val node =
      (rates \\ "exchangerate").find{ rate =>
        (((rate \@ "ccy") equals from.code) && ((rate \@ "base_ccy") equals to.code)) ||
        (((rate \@ "base_ccy") equals from.code) && ((rate \@ "ccy") equals to.code))
      }.get

      node
        .map(_ \@ "buy")
        .map(_.toDouble)
        .map(rate => if ((node \@ "base_ccy") equals from.code) 1 / rate else rate )
        .map(rate => f"$from/$to => $rate%2.4f").head
    }
  }

}
