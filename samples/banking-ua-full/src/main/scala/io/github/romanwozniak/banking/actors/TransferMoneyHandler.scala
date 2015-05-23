package io.github.romanwozniak.banking.actors

import akka.actor.{ActorLogging, Actor}
import messages._

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 23:38
 */
class TransferMoneyHandler extends Actor with ActorLogging {

  def receive = {
    case msg @ TransferMoney(from, to, currency, amount) =>



  }

}
