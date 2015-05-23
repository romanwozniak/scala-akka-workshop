package io.github.romanwozniak.exchangerates.utils

import dispatch._
import io.github.romanwozniak.banking.models.Currency
import io.github.romanwozniak.exchangerates.models.exceptions._

import scala.util.Random

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 18:32
 */
trait MixinProbabilisticException extends ExchangeRatesService {self: ExchangeRatesService =>

  abstract override def getRates(from: Currency, to: Currency): Future[String] = {
    failWithProbability(0.42, ServiceUnavailableException)
    failWithProbability(0.05, ForbiddenException)
    failWithProbability(0.3, UnauthorizedException)
    super.getRates(from, to)
  }

  private def failWithProbability(p: Double, e: Throwable): Unit = {
    if ((1-p) < Random.nextDouble()) throw e
  }

}
