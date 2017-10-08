package service

trait DatabaseConfig extends Config {
  val driver = slick.jdbc.PostgresProfile

  import driver.api._

  def db: Database = Database.forConfig("database")

  implicit val session: Session = db.createSession()
}
