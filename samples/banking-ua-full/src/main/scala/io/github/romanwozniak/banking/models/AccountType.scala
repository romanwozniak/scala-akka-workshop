package io.github.romanwozniak.banking.models

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 13:12
 */
abstract sealed class AccountType(val name: String)

case object CurrentAccountType extends AccountType("current")
case object DepositAccountType extends AccountType("deposit")
case object CreditAccountType extends AccountType("credit")
