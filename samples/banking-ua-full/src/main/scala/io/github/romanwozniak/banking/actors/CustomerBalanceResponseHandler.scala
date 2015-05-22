package io.github.romanwozniak.banking.actors

import akka.actor.{Props, ActorRef, ActorLogging, Actor}
import messages._ 
import scala.collection.mutable.ListBuffer
import scala.concurrent.duration._

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 14:11
 */
object CustomerBalanceResponseHandler {

  def props(waitingForResponses: Int, originalSender: ActorRef) = Props(classOf[CustomerBalanceResponseHandler], waitingForResponses, originalSender)

}

private[actors] class CustomerBalanceResponseHandler(waitingForResponses: Int, originalSender: ActorRef)
  extends Actor with ActorLogging {

  var responses: ListBuffer[CustomerAccounts] = ListBuffer()

  def receive = {
    case msg: CustomerAccounts =>
      responses += msg
      if (responses.size == waitingForResponses) {
        timeoutTimer.cancel()
        originalSender ! CustomerAccounts(responses.map(_.accounts).toList.flatten)
        context.stop(self)
      }

    case msg @ AccountsRetrievalTimeout =>
      originalSender ! msg
      context.stop(self)
  }

  import context.dispatcher
  val timeoutTimer = context.system.scheduler.scheduleOnce(5 seconds, self, AccountsRetrievalTimeout)

}
