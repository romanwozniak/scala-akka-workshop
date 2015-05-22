package io.github.romanwozniak.banking.models

import scala.util.Random

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:19
 */

object Currency {
  private val currencies = UAH :: USD :: EUR :: Nil

  def apply(code: String) = currencies.find(_.code.equals(code)).getOrElse(throw new Exception(s"Unknown currency: $code"))

  def random = currencies(Random.nextInt(currencies.size))
}

abstract sealed class Currency(val code: String)

case object USD extends Currency("USD")
case object EUR extends Currency("EUR")
case object UAH extends Currency("UAH")
