import model.dao.BaseDao
import model.Customer
import service.MigrationConfig

import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.event.{LoggingAdapter, NoLogging}
import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration._

trait BaseServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with Routes with MigrationConfig with BaseDao {
  protected val log: LoggingAdapter = NoLogging

  import driver.api._

  val testCustomers = Seq(
    Customer(Some(1), "customerName1", 21, 1),
    Customer(Some(2), "customerName2", 20, 2),
    Customer(Some(3), "customerName3", 60, 1)
  )
  reloadSchema()

  Await.result(customersTable ++= testCustomers, 10.seconds)
}
