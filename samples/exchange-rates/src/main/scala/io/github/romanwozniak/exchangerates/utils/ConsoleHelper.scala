package io.github.romanwozniak.exchangerates.utils

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 15:43
 */
trait ConsoleHelper {

  def clearScreen() = print("\033[2J")

  def printScreen(title: String, content: => String) = {
    clearScreen()

    val titleDecorated =  title.map(_.toUpper).mkString(" ")

    println(
      s"""
         |************************************************************
         |${(0 to ((60 - titleDecorated.length) / 2)).map(_ => " ").mkString}$titleDecorated
         |************************************************************
         |$content""".stripMargin)

  }

}
