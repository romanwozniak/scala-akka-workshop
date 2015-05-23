package io.github.romanwozniak.banking.actors.messages

import io.github.romanwozniak.banking.models.{Currency, Account, AccountType, Customer}

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:39
 */
object `package` {

  case class GetCustomerAccounts(customer: Customer, accountType: Option[AccountType] = None)
  case class CustomerAccounts(accounts: List[Account])
  case object AccountsRetrievalTimeout

  case class TransferMoney(from: Long, to: Long, currency: Currency, amount: BigDecimal)
  case class WithdrawMoney(from: Long, currency: Currency, amount: BigDecimal)

  case class GetExchangeRate(from: Currency, to: Currency)
  case class ExchangeRate(from: Currency, to: Currency, rate: Double)

}
