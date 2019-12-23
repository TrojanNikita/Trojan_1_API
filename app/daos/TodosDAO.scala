package  daos

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import models.Todo
import scala.concurrent.{ Future, ExecutionContext }


@Singleton
class TodosDAO @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  private class TodosTable(tag: Tag) extends Table[Todo](tag, "todo") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def done = column[Boolean]("done")
    def priority = column[Int]("priority")

    def * = (id, name, done, priority) <> ((Todo.apply _).tupled, Todo.unapply)
  }

  private val todos = TableQuery[TodosTable]



  def create(name: String, done: Boolean, priority:Int): Future[Todo] = db.run {
    (todos.map(p => (p.name, p.done,p.priority))
      returning todos.map(_.id)
      into ((nameDone, id) => Todo(id, nameDone._1, nameDone._2, nameDone._3))
    ) += (name, done, priority)
  }

  def findById(id: Long): Future[Option[Todo]] =
    db.run(todos.filter(_.id === id).result.headOption)

  def list(): Future[Seq[Todo]] = db.run {
    todos.result
  }

  def count(): Future[Int] = {
    db.run(todos.map(_.id).length.result)
  }
  def count(filter: String): Future[Int] = {
    db.run(todos.filter { todo => todo.name.toLowerCase like filter.toLowerCase }.length.result)
  }

  def insert(t: Seq[Todo]): Future[Unit] =
    db.run(this.todos ++= t).map(_ => ())

  def insert(t: Todo): Future[Unit] =
    db.run(todos += t).map(_ => ())



  def update(id: Long, newName: String): Future[Boolean] =
    db.run(todos.filter(_.id === id).map(_.name).update(newName).map( _ >0))


    
  def updateDone(id: Long, newDone:Boolean): Future[Unit] =
    db.run(todos.filter(_.id === id).map(_.done).update(!newDone).map( _ > 0))


    def updateAllDone(newDone:Boolean): Future[Boolean] =
    db.run(todos.filter(_.done === newDone).map(_.done).update(!newDone).map( _ > 0))




  def updatePriority(id: Long, newPriority:Int): Future[Boolean] =
  db.run(todos.filter(_.id === id).map(_.priority).update(newPriority).map( _ > 0))


  def deleteAll(b:Boolean): Future[Unit] =
  db.run(todos.filter(_.done === b).delete).map(_ => ())


  def delete(id: Long): Future[Unit] = db.run{
    todos.filter(_.id === id).delete.map(_ => ())
  }







}