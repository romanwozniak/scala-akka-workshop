package io.github.romanwozniak.banking.actors

import akka.actor.ActorSystem
import io.github.romanwozniak.banking.models.{DepositAccountType, CreditAccountType, CurrentAccountType}
import io.github.romanwozniak.banking.repositories.AccountsRepositoryImpl

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:35
 */
object AkkaSystem {

  lazy val system = ActorSystem("Banking")

  lazy val currentAccountHandler = system.actorOf(CurrentAccountsHandler.props(AccountsRepositoryImpl), "currentAccounts")
  lazy val creditAccountHandler = system.actorOf(CreditAccountsHandler.props(AccountsRepositoryImpl), "creditAccounts")
  lazy val depositAccountHandler = system.actorOf(DepositAccountsHandler.props(AccountsRepositoryImpl), "depositAccounts")

  lazy val customerBalanceActor = system.actorOf(
    CustomerBalanceHandler.props(
      (CurrentAccountType, currentAccountHandler),
      (CreditAccountType, creditAccountHandler),
      (DepositAccountType, depositAccountHandler)
    ), "balanceHandler")
}
