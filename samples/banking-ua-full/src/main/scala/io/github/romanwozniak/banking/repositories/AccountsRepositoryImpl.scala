package io.github.romanwozniak.banking.repositories

import io.github.romanwozniak.banking.models._

import scala.util.Random

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 14:33
 */
object AccountsRepositoryImpl extends AccountRepository {

  private[repositories] val accounts = {
    (1 to 20).map(id =>
      CurrentAccount(id, BigDecimal(Random.nextDouble() * Random.nextInt(200000)), Currency.random, CustomersRepositoryImpl.nextCustomer().id)
    ).toList :::
    (21 to 30).map(id =>
      DepositAccount(id, BigDecimal(Random.nextDouble() * Random.nextInt(200000)), Currency.random, CustomersRepositoryImpl.nextCustomer().id)
    ).toList :::
    (21 to 30).map(id =>
      CreditAccount(id, BigDecimal(Random.nextDouble() * Random.nextInt(200000)), Currency.random, CustomersRepositoryImpl.nextCustomer().id)
    ).toList ::: Nil
  }.toBuffer

  def findAccounts(customerId: Long, accountType: Option[AccountType] = None, currency: Option[Currency] = None) = {

    accounts
      .filter(_.customerId == customerId)
      .filter(acc => accountType.map(_ == acc.`type`).getOrElse(true))
      .toList

  }

}

trait AccountRepository {

  def findAccounts(
                             customerId: Long,
                             accountType: Option[AccountType] = None,
                             currency: Option[Currency] = None): List[Account]

}

