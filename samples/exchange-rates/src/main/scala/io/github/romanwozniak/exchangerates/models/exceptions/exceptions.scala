package io.github.romanwozniak.exchangerates.models.exceptions

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 18:13
 */
object `package` {

  case object ServiceUnavailableException extends Exception("Service Unavailable")

  case object UnauthorizedException extends Exception("Unauthorized")

  case object ForbiddenException extends Exception("Forbidden")

}
