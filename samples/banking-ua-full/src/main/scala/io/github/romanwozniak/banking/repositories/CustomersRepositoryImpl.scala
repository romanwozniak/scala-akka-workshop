package io.github.romanwozniak.banking.repositories

import io.github.romanwozniak.banking.models.Customer

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 14:34
 */
object CustomersRepositoryImpl extends CustomersRepository {

  private[repositories] val customers = {
    val firstNames = List("Andrew", "Joan", "Fred", "John", "Alex", "Eugene", "Julia", "Marina", "George")
    val lastNames = List("Beckham", "Black", "Braxton", "Brennan", "Brock", "Bryson", "Cadwell", "Cage", "Carson", "Chandler", "Cohen", "Cole", "Corbin", "Dallas", "Dalton", "Dane", "Donovan", "Easton", "Fisher", "Fletcher", "Grady", "Greyson", "Griffin", "Gunner", "Hayden", "Hudson", "Hunter", "Jacoby", "Jagger", "Jaxon", "Jett", "Kade", "Kane")

    (1 to 40)
      .map(
        id => Customer(
          id,
          firstNames(Random.nextInt(firstNames.length)),
          lastNames(Random.nextInt(lastNames.length))
        )
      ).toBuffer
  }

  private[repositories] def nextCustomer() = {
    customers(Random.nextInt(customers.size))
  }
  
  def findById(id: Long) = customers.find(_.id == id)

}

trait CustomersRepository {
  
  def findById(id: Long): Option[Customer]
  
}

