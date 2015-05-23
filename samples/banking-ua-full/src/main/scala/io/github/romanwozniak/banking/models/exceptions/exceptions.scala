package io.github.romanwozniak.banking.models.exceptions

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 22:09
 */
object `package` {

  case object AccountNotFoundException extends Exception("Account not found")
  case object NotEnoughMoneyException extends Exception("Not enough money to perform this operation")

  case object OperationIsNotPermitted extends Exception("It's not allowed to withdraw money from deposit account")
}
