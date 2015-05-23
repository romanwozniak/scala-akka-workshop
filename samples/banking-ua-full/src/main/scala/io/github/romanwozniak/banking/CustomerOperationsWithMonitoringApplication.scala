package io.github.romanwozniak.banking

import akka.actor.{ReceiveTimeout, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import io.github.romanwozniak.banking.monitoring.KamonMetrics
import io.github.romanwozniak.banking.utils.RandomHelper
import scala.concurrent.duration._
import io.github.romanwozniak.banking.actors.CustomerOperationsSupervision
import io.github.romanwozniak.banking.actors.messages._
import io.github.romanwozniak.banking.repositories.{AccountsRepositoryImpl, CustomersRepositoryImpl}

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 23:45
 */
object CustomerOperationsWithMonitoringApplication extends App with RandomHelper with KamonMetrics {
  implicit val timeout = Timeout(5 seconds)

  val bankingSystem = ActorSystem("BankingSystem")

  val customersSupervisor = bankingSystem.actorOf(
    CustomerOperationsSupervision.props(CustomersRepositoryImpl, AccountsRepositoryImpl),
    "CustomersSupervisor"
  )

  import bankingSystem.dispatcher

  bankingSystem.scheduler.schedule(0 seconds, 1000 milliseconds) {
    val (customer, account) = randomCustomerWithAccount
    val amount = randomAmount

    ( customersSupervisor ? WithdrawMoneyRequest(customer.id, WithdrawMoney(account.id, amount)) ) map {
      case OperationCompletedSuccessfully =>
        println(
          s"""
            |${customer.firstName} ${customer.lastName} requested to withdraw ${amount}${account.currency.code}
            |-------------------------------------------------------------------------------
            |result – SUCCESSFUL
          """.stripMargin)
      case OperationFailed(error, _) =>
        println(
          s"""
             |${customer.firstName} ${customer.lastName} requested to withdraw ${amount}${account.currency.code}
              |-------------------------------------------------------------------------------
              |result – FAILURE
              |reason – ${error}
          """.stripMargin)
      case ReceiveTimeout =>
    }
  }


  bankingSystem.scheduler.schedule(0 seconds, 20000 milliseconds) {
    val (customer, account) = randomCustomerWithAccount
    val amount = randomAmount

    ( customersSupervisor ? DepositMoneyRequest(customer.id, DepositMoney(account.id, amount)) ) map {
      case OperationCompletedSuccessfully =>
        println(
          s"""
             |${customer.firstName} ${customer.lastName} requested to deposit ${amount}${account.currency.code}
              |-------------------------------------------------------------------------------
              |result – SUCCESSFUL
          """.stripMargin)
      case OperationFailed(error, _) =>
        println(
          s"""
             |${customer.firstName} ${customer.lastName} requested to deposit ${amount}${account.currency.code}
              |-------------------------------------------------------------------------------
              |result – FAILURE
              |reason – ${error}
          """.stripMargin)
      case ReceiveTimeout =>
    }
  }




}
