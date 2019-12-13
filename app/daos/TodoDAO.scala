package daos

import  javax.inject.{Inject, Singleton}

import scala.concurrent.{ ExecutionContext, Future }
import models.Todo
import play.api.db.slick.{HasDatabaseConfig ,DatabaseConfigProvider,HasDatabaseConfigProvider}
 import  slick.jdbc.JdbcProfile


//
//trait TodosComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
//  import profile.api._
//
//  class Todos(tag: Tag) extends Table[Todo](tag, "TODO") {
//    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
//    def name = column[String]("NAME")
//    def * = (id.?, name) <> (Todo.tupled, Todo.unapply _)
//  }
//}
//
//
//@Singleton()
//class TodoDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
//  extends TodosComponent
//    with HasDatabaseConfigProvider[JdbcProfile] {
//
//  import profile.api._
//
//  val todos = TableQuery[Todos]
//
//  /** Construct the Map[String,String] needed to fill a select options set */
//  def options(): Future[Seq[(String, String)]] = {
//    val query = (for {
//      todo <- todos
//    } yield (todo.id, todo.name)).sortBy( /*name*/ _._2)
//
//    db.run(query.result).map(rows => rows.map { case (id, name) => (id.toString, name) })
//  }
//
//  /** Insert a new company */
//  def insert(todo: Todo): Future[Unit] =
//    db.run(todos += todo).map(_ => ())
//
//  /** Insert new companies */
//  def insert(companies: Seq[Todo]): Future[Unit] =
//    db.run(this.todos ++= todos).map(_ => ())
//}