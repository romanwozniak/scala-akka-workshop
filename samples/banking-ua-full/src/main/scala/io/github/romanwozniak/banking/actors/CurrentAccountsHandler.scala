package io.github.romanwozniak.banking.actors

import akka.actor.{Props, ActorLogging, Actor}
import io.github.romanwozniak.banking.models.CurrentAccountType
import io.github.romanwozniak.banking.repositories.AccountRepository
import messages._

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:47
 */
object CurrentAccountsHandler {
  def props(repo: AccountRepository) = Props(classOf[CurrentAccountsHandler], repo)
}

class CurrentAccountsHandler(repository: AccountRepository) extends Actor with ActorLogging {

  def receive = {
    case GetCustomerAccounts(customer, _) =>
      sender() ! CustomerAccounts(repository.findAccounts(customer.id, Some(CurrentAccountType)))
  }

}

