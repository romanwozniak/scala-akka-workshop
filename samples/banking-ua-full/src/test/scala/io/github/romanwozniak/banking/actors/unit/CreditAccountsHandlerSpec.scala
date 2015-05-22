package io.github.romanwozniak.banking.actors.unit

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import akka.pattern.ask
import akka.util.Timeout
import io.github.romanwozniak.banking.actors.CreditAccountsHandler
import io.github.romanwozniak.banking.models.{CreditAccountType, Customer}
import io.github.romanwozniak.banking.repositories.AccountRepository
import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

import io.github.romanwozniak.banking.actors.messages._

import scala.util.Success
import scala.concurrent.duration._

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 22:20
 */
class CreditAccountsHandlerSpec extends FlatSpec with Matchers with MockitoSugar {

  implicit val timeout = Timeout(100 milliseconds)
  implicit val system = ActorSystem("TestSystem")

  it should "return an empty list when there are no accounts for such customer" in {

    val customer = Customer(1, "Akka", "Test")
    val accountRepoMock = mock[AccountRepository]

    when(accountRepoMock.findAccounts(customer.id, Some(CreditAccountType))) thenReturn List()

    val testActor = TestActorRef(new CreditAccountsHandler(accountRepoMock))

    val Success(CustomerAccounts(accounts)) = (testActor ? GetCustomerAccounts(customer)).mapTo[CustomerAccounts].value.get

    accounts should be('empty)
  }

}
