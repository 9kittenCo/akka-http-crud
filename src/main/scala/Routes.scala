import api.{ApiErrorHandler, CustomersApi}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

trait Routes extends ApiErrorHandler with CustomersApi{
  val routes: Route =
    pathPrefix("v1"){
      customersApi
    }
}
