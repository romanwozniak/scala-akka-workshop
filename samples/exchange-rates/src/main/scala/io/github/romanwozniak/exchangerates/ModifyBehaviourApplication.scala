package io.github.romanwozniak.exchangerates

import akka.actor.{PoisonPill, Props, ActorSystem}
import akka.pattern.ask
import akka.routing.{ScatterGatherFirstCompletedPool, RoundRobinPool}
import io.github.romanwozniak.banking.models.{EUR, USD, UAH}
import io.github.romanwozniak.exchangerates.actors.ExchangeRatesActor
import io.github.romanwozniak.banking.actors.messages._
import io.github.romanwozniak.exchangerates.actors.messages._
import io.github.romanwozniak.exchangerates.utils.{NBUExchangeRates, YahooExchangeRates, HttpDependent, ConsoleHelper}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 15:41
 */
object ModifyBehaviourApplication extends App with ConsoleHelper with HttpDependent {

  lazy val exchangeRatesSystem = ActorSystem("ExchangeRates")

  val exchangeActor = exchangeRatesSystem.actorOf(
    ExchangeRatesActor.props(YahooExchangeRates.getRates, NBUExchangeRates.getRates), "exchangeRates")

  appLoop()

  def appLoop(): Unit = {
    printScreen("Exchange Rates", {
      s"""
         |[1] Yahoo Exchange Rates
         |[2] NBU Exchange Rates
         |
         |[0] Exit""".stripMargin
    })
    StdIn.readLine() match {
      case "1" =>
        yahoo()  
      case "2" =>
        nbu()
      case "0" =>
        shutdown()
      case _ => appLoop()
    }
  }

  def yahoo() = {
    exchangeActor ! BecomeYahooConverter
    showRates("Yahoo Exchange Rates")
  }

  def nbu() = {
    exchangeActor ! BecomeNBUConverter
    showRates("NBU Exchange Rates")
  } 
  
  def showRates(title: String) = {
    for {
      uahUsd <- (exchangeActor ? GetExchangeRate(USD, UAH))(5 seconds).mapTo[String]
      uahEur <- (exchangeActor ? GetExchangeRate(EUR, UAH))(5 seconds).mapTo[String]
    } {
      printScreen(title, {
        s"""
           |$uahUsd
           |$uahEur
           |
           |Press any key...""".stripMargin
      })
    }
    StdIn.readLine()
    appLoop()
  }
  
  def shutdown() = {
    closeHttp()
    exchangeRatesSystem.shutdown()  
  }

}
