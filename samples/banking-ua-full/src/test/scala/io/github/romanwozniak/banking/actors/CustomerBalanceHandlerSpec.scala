package io.github.romanwozniak.banking.actors

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, ImplicitSender, TestKit}
import io.github.romanwozniak.banking.models._
import io.github.romanwozniak.banking.repositories.AccountRepository
import org.mockito.Mockito._
import org.mockito.{Matchers => MockitoMatchers};
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import scala.concurrent.duration._

import messages._

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 17:29
 */
class CustomerBalanceHandlerSpec
  extends TestKit(ActorSystem("TestSystem"))
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll
  with MockitoSugar {

  override def afterAll() {
    TestKit.shutdownActorSystem(system)
  }

  "A CustomerBalance Actor" must {
    "send back the list of customer's accounts" in {

      val customer = Customer(1, "Akka", "Test")

      val customerAccounts = List(
        CurrentAccount(1, 20.082, UAH, customer.id),
        DepositAccount(2, 25000.00, UAH, customer.id)
      )

      val accountsRepoMock = mock[AccountRepository]
      when(accountsRepoMock.findAccounts(customer.id)) thenReturn customerAccounts
      when(accountsRepoMock.findAccounts(customer.id, Some(CurrentAccountType))) thenReturn customerAccounts.filter(_.`type` == CurrentAccountType)
      when(accountsRepoMock.findAccounts(customer.id, Some(CreditAccountType))) thenReturn customerAccounts.filter(_.`type` == CreditAccountType)
      when(accountsRepoMock.findAccounts(customer.id, Some(DepositAccountType))) thenReturn customerAccounts.filter(_.`type` == DepositAccountType)

      val balanceActor = system.actorOf(CustomerBalanceHandler.props(
        (CurrentAccountType, system.actorOf(CurrentAccountsHandler.props(accountsRepoMock))),
        (CreditAccountType, system.actorOf(CreditAccountsHandler.props(accountsRepoMock))),
        (DepositAccountType, system.actorOf(DepositAccountsHandler.props(accountsRepoMock)))
      ), "testBalanceActor1")

      balanceActor ! GetCustomerAccounts(customer)
      expectMsgPF() {
        case CustomerAccounts(accounts) =>
          assert(customerAccounts.forall(accounts.contains(_)), "Result should contain all expected accounts")
          assert(accounts.forall(customerAccounts.contains(_)), "Result should contain only expected accounts")
      }

    }
  }


  "A CustomerBalance Actor" must {
    "respond with AccountsRetrievalTimeout message in case if DB layer throws exception" in {

      val customer = Customer(1, "Akka", "Test")

      val accountsRepoMock = mock[AccountRepository]
      when(
        accountsRepoMock.findAccounts(customer.id, Some(CurrentAccountType))
      ).thenThrow(new IllegalStateException("Database is unavailable"))

      val balanceActor = system.actorOf(CustomerBalanceHandler.props(
        (CurrentAccountType, system.actorOf(CurrentAccountsHandler.props(accountsRepoMock))),
        (CreditAccountType, system.actorOf(CreditAccountsHandler.props(accountsRepoMock))),
        (DepositAccountType, system.actorOf(DepositAccountsHandler.props(accountsRepoMock)))
      ), "testBalanceActor2")

      balanceActor ! GetCustomerAccounts(customer)
      expectNoMsg(5 seconds)
      expectMsg(AccountsRetrievalTimeout)

    }
  }

}
