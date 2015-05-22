package io.github.romanwozniak.banking.actors

import akka.actor.{Props, ActorLogging, Actor}
import io.github.romanwozniak.banking.actors.messages.{CustomerAccounts, GetCustomerAccounts}
import io.github.romanwozniak.banking.models.DepositAccountType
import io.github.romanwozniak.banking.repositories.AccountRepository

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:45
 */
object DepositAccountsHandler {
  def props(repo: AccountRepository) = Props(classOf[DepositAccountsHandler], repo)
}

class DepositAccountsHandler(repository: AccountRepository) extends Actor with ActorLogging {

  def receive = {
    case GetCustomerAccounts(customer, _) =>
      sender() ! CustomerAccounts(repository.findAccounts(customer.id, Some(DepositAccountType)))
  }

}
