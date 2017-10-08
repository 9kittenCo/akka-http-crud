import org.scalatest.concurrent.ScalaFutures
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.{HttpEntity, MediaTypes, StatusCode}
import model.Customer
import model.dao.CustomersDao

class CustomerServiceSpec extends BaseServiceSpec with ScalaFutures {
  "Customers service" should {
    "retrieve customers list" in {
      Get("/customers") ~> customersRoute ~> check {
        responseAs[JsArray] should be(testCustomers.toJson)
      }
    }
  }
  "retrieve customer by id" in {
    Get("/customers/1") ~> customersRoute ~> check {
      responseAs[JsObject] should be(testCustomers.headOption.toJson)
    }
  }
  "create customer properly" in {
    val newUsername = "UpdatedUsername"
    val requestEntity = HttpEntity(MediaTypes.`application/json`,
      JsObject(
        "name" -> JsString(newUsername),
        "age" -> JsNumber(testCustomers.head.age),
        "gender" -> JsNumber(testCustomers.head.gender)
      ).toString())
    Post("/customers", requestEntity) ~> customersRoute ~> check {
      response.status should be(StatusCode.int2StatusCode(200))
      Get("/customers") ~> customersRoute ~> check {
        responseAs[Seq[Customer]] should have length 4
      }
    }
  }
  "update customer by id" in {
    val newName = "UpdatedName"
    val requestEntity = HttpEntity(MediaTypes.`application/json`,
      JsObject(
        "name" -> JsString(newName),
        "age" -> JsNumber(testCustomers.head.age),
        "gender" -> JsNumber(testCustomers.head.gender)
      ).toString())
    Put("/customers/1", requestEntity) ~> customersRoute ~> check {
      response.status should be(StatusCode.int2StatusCode(200))
      whenReady(CustomersDao.findById(1)) { result =>
        result.name should be(newName)
      }
    }
  }
  "delete customer by id" in {
    Delete("/customers/1") ~> customersRoute ~> check {
      response.status should be(StatusCode.int2StatusCode(200))
      Get("/customers") ~> customersRoute ~> check {
        responseAs[Seq[Customer]] should have length 3
      }
    }
  }
}
