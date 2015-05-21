package io.romanwozniak.github.akkamodule.actors

import java.io.{StringWriter, PrintWriter, Writer}
import java.text.SimpleDateFormat
import java.util.Date

import akka.actor.Actor

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/21/15, 11:06
 */
object ConsoleLoggerActor {
  
  case class Debug(msg: String)
  case class Info(msg: String)
  case class Warning(msg: String)
  case class Error(msg: String, e: Throwable = null)
}

class ConsoleLoggerActor extends Actor {

  import io.romanwozniak.github.akkamodule.actors.ConsoleLoggerActor._

  private val timeFormat = new SimpleDateFormat("HH:mm:ss mmm")

  def receive = {
    case Debug(msg) =>
      log("DEBUG", msg)

    case Info(msg) =>
      log("INFO", msg)

    case Warning(msg) =>
      log("WARN", msg)

    case Error(msg, e) =>
      Option(e).map(ex => {
        val errors = new StringWriter()
        ex.printStackTrace(new PrintWriter(errors));
        errors.toString
      }) match  {
        case Some(info) => log("ERROR", msg, info)
        case None => log("ERROR", msg)
      }

    case _ =>
  }

  private def log(tag: String, msg: String, info: String*): Unit = {
    println(f"[${timeFormat.format(new Date())}]-[$tag%5s]- $msg")
    info foreach println
  }

}
