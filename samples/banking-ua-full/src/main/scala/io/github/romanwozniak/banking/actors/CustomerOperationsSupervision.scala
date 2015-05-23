package io.github.romanwozniak.banking.actors

import akka.actor._
import io.github.romanwozniak.banking.repositories.{CustomersRepository, AccountRepository}
import io.github.romanwozniak.banking.actors.messages._

import scala.collection.immutable.{HashMap, IndexedSeq}

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 22:54
 */
object CustomerOperationsSupervision {

  def props(customersRepository: CustomersRepository,
            accountRepository: AccountRepository) = Props(classOf[CustomerOperationsSupervision], customersRepository, accountRepository)

}

class CustomerOperationsSupervision(
  customersRepository: CustomersRepository,
  accountRepository: AccountRepository) extends Actor with ActorLogging {

  val handlers: HashMap[Long, ActorRef] = HashMap(
    customersRepository.list.map { customer =>
      val handler = context.actorOf(CustomerAccountOperationsHandler.props(customer, accountRepository), s"customer-${customer.id}")
      context.watch(handler)
      (customer.id, handler)
    } :_*)

  def receive = {
    case WithdrawMoneyRequest(customerId, msg) =>
      handlers.get(customerId).foreach(_ forward msg)

    case DepositMoneyRequest(customerId, msg) =>
      handlers.get(customerId).foreach(_ forward msg)

    case Terminated(actorRef) =>
      val Some(customerId) =
        handlers.find {
          case (_, handler) => handler == actorRef
        }.map(_._1)

      customersRepository.findById(customerId).map { customer =>
        val handler = context.actorOf(CustomerAccountOperationsHandler.props(customer, accountRepository), s"customer-${customer.id}")
        context.watch(handler)
        handlers.updated(customerId, handler)
      }
  }

}

