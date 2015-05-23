package io.github.romanwozniak.banking.actors


import akka.actor.{Props, ActorLogging, Actor}
import io.github.romanwozniak.banking.models.Customer
import io.github.romanwozniak.banking.actors.messages._
import io.github.romanwozniak.banking.models.exceptions.AccountNotFoundException
import io.github.romanwozniak.banking.repositories.AccountRepository

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 22:37
 */
object CustomerAccountOperationsHandler {

  def props(customer: Customer, accountsRepository: AccountRepository) =
    Props(classOf[CustomerAccountOperationsHandler], customer, accountsRepository)

}

class CustomerAccountOperationsHandler(
  customer: Customer,
  accountsRepository: AccountRepository) extends Actor with ActorLogging {

  def receive = {
    case WithdrawMoney(accountId, amount) =>
      val originalSender = sender()
      accountsRepository
        .findAccounts(customer.id)
        .find(_.id == accountId) match {
        case Some(account) =>
          accountsRepository.withdrawMoney(account.id, amount)
          originalSender ! OperationCompletedSuccessfully
        case None =>
          originalSender ! OperationFailed(AccountNotFoundException.getMessage, Some(AccountNotFoundException))
      }

    case DepositMoney(accountId, amount) =>
      val originalSender = sender()
      accountsRepository
        .findAccounts(customer.id)
        .find(_.id == accountId) match {
        case Some(account) =>
          accountsRepository.depositMoney(account.id, amount)
          originalSender ! OperationCompletedSuccessfully
        case None =>
          originalSender ! OperationFailed(AccountNotFoundException.getMessage, Some(AccountNotFoundException))
      }

  }

}
