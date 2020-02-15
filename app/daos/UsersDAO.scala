package  daos

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import models.User
import scala.concurrent.{ Future, ExecutionContext }


@Singleton
class UsersDAO @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

	private val dbConfig = dbConfigProvider.get[JdbcProfile]

	import dbConfig._
	import profile.api._


	private class UsersTable(tag: Tag) extends Table[User](tag, "users") {

		def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
		
		def username = column[String]("username")

		def password = column[String]("password")

		def email = column[String]("email")


		def * = (id, username, password, email) <> ((User.apply _).tupled, User.unapply)
	}

	private val users = TableQuery[UsersTable]



  def findById(id: Long): Future[Option[User]] =
    db.run(users.filter(_.id === id).result.headOption)

  def list(): Future[Seq[User]] = db.run {
    users.result
  }


	def findByUsername(username: String): Future[Option[User]] = db.run {
		users.filter(_.username === username).result.headOption
	}

	// def insert(email: String, password: String, name: String): Future[(Long, User)] = db.run{
	// 	users.insert(User(_, email, password, name, emailConfirmed = false, active = false))
	// }

	def create(user:User): Future[User] = db.run {
		(users.map(p => (p.username, p.password, p.email))
		returning users.map(_.id)
		into ((userInfo, id) => User(id, userInfo._1, userInfo._2, userInfo._3))
		) += (user.username, user.password, user.email)
	}


	def update(id: Long, newUsername: String): Future[Boolean] =
		db.run(users.filter(_.id === id).map(_.username).update(newUsername).map( _ >0))


	def updatePassword(id: Long, newPassword: String): Future[Boolean] =
		db.run(users.filter(_.id === id).map(_.password).update(newPassword).map( _ >0))





	// def delete(id: Long): Future[Unit] = db.run{
	// 	users.delete(id)
	// }

	// def list: Future[Seq[User]] = db.run{
	// 	users.values
	// }
//sortBy(_.name)



}