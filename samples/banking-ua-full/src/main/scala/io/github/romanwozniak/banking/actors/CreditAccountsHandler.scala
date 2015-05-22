package io.github.romanwozniak.banking.actors

import akka.actor.{Props, ActorLogging, Actor}
import io.github.romanwozniak.banking.models.{CreditAccountType, Customer}
import io.github.romanwozniak.banking.repositories.{AccountsRepositoryImpl, AccountRepository}
import messages._

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:46
 */
object CreditAccountsHandler {
  def props(repo: AccountRepository) = Props(classOf[CreditAccountsHandler], repo)
}

class CreditAccountsHandler(repository: AccountRepository) extends Actor with ActorLogging {

  def receive = {
    case GetCustomerAccounts(customer, _) =>
      sender() ! CustomerAccounts(repository.findAccounts(customer.id, Some(CreditAccountType)))
  }

}
