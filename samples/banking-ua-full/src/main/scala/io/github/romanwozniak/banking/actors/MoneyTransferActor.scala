package io.github.romanwozniak.banking.actors

import akka.actor._
import io.github.romanwozniak.banking.repositories.{TransactionRepository, AccountRepository}
import scala.concurrent.duration._

import messages._
import io.github.romanwozniak.banking.models.Account

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 23:50
 */
class MoneyTransferActor(
  exchangeRatePath: String,
  accountsRepository: AccountRepository,
  transactionRepository: TransactionRepository) extends Actor with ActorLogging {

  private case object LookupTimeout

  val messageId = "exchangeRate"

  def exchangeRatesLookUp = {
    context.actorSelection(exchangeRatePath) ! Identify(messageId)

    import context.dispatcher
    context.system.scheduler.scheduleOnce(1 second, self, LookupTimeout)
  }

  def receive = lookingForExchangeRate


  def lookingForExchangeRate: Receive = {
    case ActorIdentity(`messageId`, Some(exchangeRate)) =>
      context.watch(exchangeRate)
      context.become(doTransfer(exchangeRate))

    case ActorIdentity(`messageId`, _) =>

    case LookupTimeout => exchangeRatesLookUp
    case _ =>

  }

  def doTransfer(exchangeRates: ActorRef): Receive = {
    case TransferMoney(fromId, toId, _, amount) =>

  }

}
