package io.github.romanwozniak.banking.models

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:06
 */
abstract class Account(
  val id: Long,
  val `type`: AccountType,
  val amount: BigDecimal,
  val currency: Currency,
  val customerId: Long)

case class DepositAccount(
  override val id: Long,
  override val amount: BigDecimal,
  override val currency: Currency,
  override val customerId: Long) extends Account(id, DepositAccountType, amount, currency, customerId)

case class CreditAccount(
  override val id: Long,
  override val amount: BigDecimal,
  override val currency: Currency,
  override val customerId: Long) extends Account(id, CreditAccountType, amount, currency, customerId)

case class CurrentAccount(
  override val id: Long,
  override val amount: BigDecimal,
  override val currency: Currency,
  override val customerId: Long) extends Account(id, CurrentAccountType, amount, currency, customerId)