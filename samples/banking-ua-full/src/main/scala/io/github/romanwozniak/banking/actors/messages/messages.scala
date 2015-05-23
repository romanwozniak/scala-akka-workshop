package io.github.romanwozniak.banking.actors.messages

import akka.routing.ConsistentHashingRouter.ConsistentHashable
import io.github.romanwozniak.banking.models.{Currency, Account, AccountType, Customer}

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:39
 */
object `package` {

  case class GetCustomerAccounts(customer: Customer, accountType: Option[AccountType] = None)
  case class CustomerAccounts(accounts: List[Account])
  case object AccountsRetrievalTimeout


  case class WithdrawMoneyRequest(customerId: Long, withdrawMoney: WithdrawMoney)
  case class WithdrawMoney(accountId: Long, amount: BigDecimal)
  case class DepositMoneyRequest(customerId: Long, depositMoney: DepositMoney)
  case class DepositMoney(accountId: Long, amount: BigDecimal)
  case object OperationCompletedSuccessfully
  case class OperationFailed(error: String, e: Option[Throwable] = None)

  case class GetExchangeRate(from: Currency, to: Currency)
  case class ExchangeRate(from: Currency, to: Currency, rate: Double)

}
