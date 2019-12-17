package  daos

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import models.Todo
import scala.concurrent.{ Future, ExecutionContext }

/**
 * A repository for people.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class TodosDAO @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._

  /**
   * Here we define the table. It will have a name of people
   */
  private class TodosTable(tag: Tag) extends Table[Todo](tag, "TODO") {

    /** The ID column, which is the primary key, and auto incremented */
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    /** The name column */
    def name = column[String]("NAME")

    /** The name column */
    def done = column[Boolean]("DONE")

//    /** The age column */
//    def age = column[Int]("age")

    /**
     * This is the tables default "projection".
     *
     * It defines how the columns are converted to and from the Person object.
     *
     * In this case, we are simply passing the id, name and page parameters to the Person case classes
     * apply and unapply methods.
     */
    def * = (id, name, done) <> ((Todo.apply _).tupled, Todo.unapply)
  }

  /**
   * The starting point for all queries on the people table.
   */
  private val todos = TableQuery[TodosTable]

  /**
   * Create a person with the given name and age.
   *
   * This is an asynchronous operation, it will return a future of the created person, which can be used to obtain the
   * id for that person.
   */
  // def create(name: String): Future[Todo] = db.run {
  //   // We create a projection of just the name and age columns, since we're not inserting a value for the id column
  //   (todos.map(todo => (todo.name, todo.done))
  //     // Now define it to return the id, because we want to know what id was generated for the person
  //     returning todos.map(_.id)
  //     // And we define a transformation for the returned value, which combines our original parameters with the
  //     // returned id
  //     into (((name,done), id) => Todo(id, name, done))
  //     // And finally, insert the person into the database
  //     ) += (name,done)
  // }


  def create(name: String, done: Boolean): Future[Todo] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    (todos.map(p => (p.name, p.done))
      // Now define it to return the id, because we want to know what id was generated for the person
      returning todos.map(_.id)
      // And we define a transformation for the returned value, which combines our original parameters with the
      // returned id
      into ((nameDone, id) => Todo(id, nameDone._1, nameDone._2))
    // And finally, insert the person into the database
    ) += (name, done)
  }

  /** Retrieve a computer from the id. */
  def findById(id: Long): Future[Option[Todo]] =
    db.run(todos.filter(_.id === id).result.headOption)

//  val findById = for {
//    id <- Parameters[Long]
//    c <- this if c.id is id
//  } yield c

  /**
   * List all the people in the database.
   */
  def list(): Future[Seq[Todo]] = db.run {
    todos.result
  }

  /** Count all todos. */
  def count(): Future[Int] = {
    // this should be changed to
    // db.run(computers.length.result)
    // when https://github.com/slick/slick/issues/1237 is fixed
    db.run(todos.map(_.id).length.result)
  }
  /** Count todos with a filter. */
  def count(filter: String): Future[Int] = {
    db.run(todos.filter { todo => todo.name.toLowerCase like filter.toLowerCase }.length.result)
  }

  /** Insert new computers. */
  def insert(t: Seq[Todo]): Future[Unit] =
    db.run(this.todos ++= t).map(_ => ())

  /** Insert a new computer. */
  def insert(t: Todo): Future[Unit] =
    db.run(todos += t).map(_ => ())



  def update(id: Long, newName: String): Future[Boolean] =
    db.run(todos.filter(_.id === id).map(_.name).update(newName).map( _ > 0))


    
  def updateDone(id: Long, newDone:Boolean): Future[Boolean] =
    db.run(todos.filter(_.id === id).map(_.done).update(!newDone).map( _ > 0))

  def deleteAll(b:Boolean): Future[Unit] =
  db.run(todos.filter(_.done === true).delete).map(_ => ())


  /** Delete a computer. */
  def delete(id: Long): Future[Unit] =
    db.run(todos.filter(_.id === id).delete).map(_ => ())

//
//  def delete(id: Long): Option[Int] = {
//    val originalSize = todos.length
//    todos = todos.filterNot(_.id == id)
//    Some(originalSize - todos.length) // returning the number of deleted users
//  }






}