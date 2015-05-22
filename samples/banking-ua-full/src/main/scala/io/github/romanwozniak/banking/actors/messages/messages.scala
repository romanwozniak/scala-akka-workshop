package io.github.romanwozniak.banking.actors.messages

import io.github.romanwozniak.banking.models.{Account, AccountType, Customer}

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:39
 */
object `package` {

  case class GetCustomerAccounts(customer: Customer, accountType: Option[AccountType] = None)
  case class CustomerAccounts(accounts: List[Account])

}
