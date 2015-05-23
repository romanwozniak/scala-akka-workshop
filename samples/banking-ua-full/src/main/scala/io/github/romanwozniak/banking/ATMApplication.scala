package io.github.romanwozniak.banking

import akka.pattern.ask
import actors.AkkaSystem._
import actors.messages._
import io.github.romanwozniak.banking.models.Customer
import io.github.romanwozniak.banking.monitoring.KamonMetrics
import io.github.romanwozniak.banking.repositories.CustomersRepositoryImpl
import io.github.romanwozniak.banking.utils.ConsoleHelper
import scala.concurrent.ExecutionContext.Implicits.global

import scala.io.StdIn
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:26
 */
object ATMApplication extends App with ConsoleHelper with KamonMetrics {

  appLoop()

  def appLoop(): Unit = {
    printScreen("Welcome",
    """
      |Please, enter your customer id:""".stripMargin)

    Try(StdIn.readLine().toLong) match {
      case Success(id) =>
        CustomersRepositoryImpl.findById(id.toLong) match {

          case Some(customer) =>
            customerScreen(customer)

          case None =>
            printScreen("Error",
              """
                |Customer with such id not found...""".stripMargin)
            StdIn.readLine()
            appLoop()
        }
      case _ => appLoop()
    }
  }

  def customerScreen(customer: Customer): Unit = {
    printScreen(s"Hi, ${customer.firstName} ${customer.lastName}",
      """
        |Choose, what you want to do:
        |
        |[1] - Show accounts
        |[0] - Exit""".stripMargin)

    StdIn.readLine() match {
      case "1" =>
        (customerBalanceActor ? GetCustomerAccounts(customer))(10 seconds).map {
          case CustomerAccounts(accounts) =>
            printScreen("Customer accounts",
              accounts match {
                case Nil =>
                  """
                    |No accounts found...""".stripMargin
                case _ =>
                  accounts.groupBy(_.`type`).map {
                    case (accType, accs) =>
                      s"""
                         |${accType.name}
                         |------------------------------------------------------------
                         |${accs.map(acc => f"${acc.id}%-5d${acc.currency.code}%-5s${acc.amount}%20.2f").mkString("\n")}""".stripMargin
                  }.mkString("\n")
              }


            )
            StdIn.readLine()
            appLoop()
        }
      case "0" => exit()
      case _   => customerScreen(customer)
    }
  }

  def exit() = {
    system.shutdown()
  }

}
