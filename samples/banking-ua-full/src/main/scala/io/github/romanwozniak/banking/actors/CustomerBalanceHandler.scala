package io.github.romanwozniak.banking.actors

import akka.actor.{Props, ActorRef, ActorLogging, Actor}
import io.github.romanwozniak.banking.actors.messages._
import io.github.romanwozniak.banking.models.AccountType

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:38
 */
object CustomerBalanceHandler {

  def props(handlers: (AccountType, ActorRef)*) = Props(classOf[CustomerBalanceHandler], handlers.toMap)

}

class CustomerBalanceHandler(accountHandlers: Map[AccountType, ActorRef])
  extends Actor with ActorLogging {

  def receive = {

    case msg @ GetCustomerAccounts(customer, None) =>
      val originalSender = sender()
      val responseHandler = context.actorOf(CustomerBalanceResponseHandler.props(accountHandlers.size, originalSender))
      accountHandlers.values.foreach(_.tell(msg, responseHandler))


    case msg @ GetCustomerAccounts(customer, Some(accountType)) =>
      val typedAccountHandlers = accountHandlers.filter(_._1 == accountType).values
      val originalSender = sender()
      val responseHandler = context.actorOf(CustomerBalanceResponseHandler.props(typedAccountHandlers.size, originalSender))
      typedAccountHandlers.foreach(_.tell(msg, responseHandler))

  }


}
