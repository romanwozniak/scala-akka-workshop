package io.romanwozniak.github.akkamodule

import actors.MyActorSystem._
import io.romanwozniak.github.akkamodule.actors.ConsoleLoggerActor.{Error, Warning, Debug, Info}

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/21/15, 11:22
 */
object ConsoleLogger {

  def debug(msg: String) = dummyActor ! Debug(msg)
  def info(msg: String) = dummyActor ! Info(msg)
  def warn(msg: String) = dummyActor ! Warning(msg)
  def error(msg: String) = dummyActor ! Error(msg)
  def error(msg: String, ex: Throwable) = dummyActor ! Error(msg, ex)

}
