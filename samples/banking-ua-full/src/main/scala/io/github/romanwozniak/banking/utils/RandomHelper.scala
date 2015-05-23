package io.github.romanwozniak.banking.utils

import io.github.romanwozniak.banking.models.{Account, Customer}
import io.github.romanwozniak.banking.repositories.{AccountsRepositoryImpl, CustomersRepositoryImpl}

import scala.util.Random

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 23:52
 */
trait RandomHelper {
  
  def randomCustomerWithAccount: (Customer, Account) = {
    val c = CustomersRepositoryImpl.list
    val customer = c(Random.nextInt(c.length))
    AccountsRepositoryImpl.findAccounts(customer.id) match {
      case Nil => randomCustomerWithAccount
      case accounts => 
        (customer, accounts(Random.nextInt(accounts.length)))
    }
  }

  def randomAmount: BigDecimal = {
    BigDecimal(((Random.nextInt(5000) + 100) * Random.nextDouble()).floor)
  }
  
  

}
