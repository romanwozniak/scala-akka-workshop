package io.github.romanwozniak.banking.actors.messages

import io.github.romanwozniak.banking.models.Currency


/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 10:55
 */
object `package`{

  case class GetExchangeRate(from: Currency, to: Currency)
  case class ExchangeRate(from: Currency, to: Currency, rate: Double)

}
