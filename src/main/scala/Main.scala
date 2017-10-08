import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import service.{Config, MigrationConfig}
import akka.http.scaladsl.server.Directives._
import scala.concurrent.ExecutionContext


object Main extends App with Config with MigrationConfig with Routes {
  private implicit val system: ActorSystem = ActorSystem()
  protected implicit val executor: ExecutionContext = system.dispatcher
  protected val log: LoggingAdapter = Logging(system, getClass)
  protected implicit val materializer: ActorMaterializer = ActorMaterializer()

  migrate()

  val bindingFuture = Http()
    .bindAndHandle(handler = logRequestResult("log")(routes)
      , interface = httpInterface, port = httpPort)

  println(s"Server online at $httpInterface:$httpPort\nPress RETURN to stop...")
  scala.io.StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())

}


