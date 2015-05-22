package io.github.romanwozniak.banking.models

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:06
 */
abstract class Account(
  val id: Long,
  val `type`: AccountType,
  val currency: Currency,
  val customerId: Long)

case class DepositAccount(
  override val id: Long,
  amount: BigDecimal,
  override val currency: Currency,
  override val customerId: Long) extends Account(id, DepositAccountType, currency, customerId)

case class CreditAccount(
  override val id: Long,
  amount: BigDecimal,
  override val currency: Currency,
  override val customerId: Long) extends Account(id, CreditAccountType, currency, customerId)

case class CurrentAccount(
  override val id: Long,
  amount: BigDecimal,
  override val currency: Currency,
  override val customerId: Long) extends Account(id, CurrentAccountType, currency, customerId)