package io.github.romanwozniak.exchangerates.utils

import dispatch._
import io.github.romanwozniak.banking.models.Currency

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 18:32
 */
trait ExchangeRatesService {

  def getRates(from: Currency, to: Currency): Future[String]

}
