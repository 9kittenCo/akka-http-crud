package model.db

import model.Customer
import slick.jdbc.PostgresProfile.api._

class CustomersTable(tag: Tag) extends Table[Customer](tag, "сustomer_info") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def age = column[Int]("age")

  def gender = column[Int]("gender")

  def * = (id.?, name, age, gender) <> ((Customer.apply _).tupled, Customer.unapply)
}
