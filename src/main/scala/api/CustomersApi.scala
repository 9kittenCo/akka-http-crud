package api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import model.Customer
import model.dao.CustomersDao
import spray.json.{DefaultJsonProtocol, RootJsonFormat, _}

import scala.concurrent.ExecutionContext.Implicits.global

trait JsonMapping extends DefaultJsonProtocol {
  implicit val customerFormat: RootJsonFormat[Customer] = jsonFormat4(Customer)
}

trait CustomersApi extends JsonMapping {
  val customersRoute: Route =
    (path("customers") & get) {
      complete(CustomersDao.findAll().map(_.toJson))
    } ~
      (path("customers" / IntNumber) & get) { id =>
        complete(CustomersDao.findById(id).map(_.toJson))
      } ~
      (path("customers") & post) {
        entity(as[Customer]) { customer =>
          complete(CustomersDao.create(customer).map(_.toJson))
        }
      } ~
      (path("customers" / IntNumber) & put) { id =>
        entity(as[Customer]) { customer =>
          complete(CustomersDao.update(id, customer).map(_.toJson))
        }
      } ~
      (path("customers" / IntNumber) & delete) { id =>
        complete(CustomersDao.delete(id).map(_.toJson))
      }
}
