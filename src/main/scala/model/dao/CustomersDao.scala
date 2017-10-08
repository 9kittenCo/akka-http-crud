package model.dao

import model.Customer
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

object CustomersDao extends BaseDao {
  def findAll(): Future[Seq[Customer]] = customersTable.result

  def findById(id: Long): Future[Customer] = customersTable.filter(_.id === id).result.head

  def create(user: Customer): Future[Long] = customersTable returning customersTable.map(_.id) += user

  def update(id: Long, newCustomer: Customer): Future[Int] = {
    customersTable.filter(_.id === id).map(customer => (customer.name, customer.age, customer.gender))
      .update((newCustomer.name, newCustomer.age, newCustomer.gender))
  }


  def delete(id: Long): Future[Int] = {
    customersTable.filter(_.id === id).delete
  }
}
