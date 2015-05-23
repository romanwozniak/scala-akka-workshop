package io.github.romanwozniak.banking.models

import java.time.temporal.TemporalAmount

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 00:34
 */
case class Transaction(
  id: Long,
  uuid: String,
  customerId: Long,
  currency: Currency,
  amount: BigDecimal,
  fromId: Long,
  toId: Option[Long]
)
