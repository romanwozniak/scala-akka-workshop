package io.github.romanwozniak.banking

import akka.pattern.ask
import actors.AkkaSystem._
import actors.messages._
import io.github.romanwozniak.banking.repositories.CustomersRepositoryImpl
import scala.concurrent.ExecutionContext.Implicits.global

import scala.io.StdIn
import scala.concurrent.duration._

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:26
 */
object Application extends App {

  appLoop()

  def appLoop(): Unit = {
    clearScreen()
    val id = StdIn.readLine(
      """
        |**************************************************
        |                 W E L C O M E
        |**************************************************
        |
        |Please, enter your customer id:
      """.stripMargin)
    CustomersRepositoryImpl.findById(id.toLong) match {

      case Some(customer) =>
        clearScreen()
        StdIn.readLine(
          s"""
            |**************************************************
            |      H I,   ${customer.firstName.map(_.toUpper).mkString(" ")}   ${customer.lastName.map(_.toUpper).mkString(" ")}
            |**************************************************
            |
            |Choose, what you want to do:
            |
            |*[1] - Show accounts
            |*[0] - Exit
          """.stripMargin).toInt match {

          case 1 =>
            (customerBalanceActor ? GetCustomerAccounts(customer))(10 seconds).map {
              case CustomerAccounts(accounts) =>
                print("\033[2J")
                println(
                s"""
                  |**************************************************
                  |          C U S T O M E R   A C C O U N T S
                  |**************************************************
                  |${accounts.groupBy(_.`type`).map {
                  case (accType, accs) =>
                    s"""
                       |${accType.name}
                       |--------------------------------------------------
                       |${accs.map(acc => f"${acc.id}%-5d${acc.currency.code}%-5s").mkString("\n")}""".stripMargin
                }.mkString("\n")}""".stripMargin
                )
                StdIn.readLine()
                appLoop()
            }

          case 0 => exit()

        }

      case None =>
        println("Customer with such id not found.")
        appLoop()
    }
  }

  def exit() = {
    system.shutdown()
  }

  def clearScreen() = print("\033[2J")

}
