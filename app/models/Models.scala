package models

import play.api.data.Form
import play.api.libs.json._

case class Todo(id: Long, name: String, done:Boolean, priority:Int)

object Todo {
  implicit val todoFormat = Json.format[Todo]
}

case class NameOfTodo(name: String)
object NameOfTodo {
  implicit val todoFormat = Json.format[NameOfTodo]
}
case class IdOfTodo(id: Long)
object IdOfTodo {
  implicit val todoFormat = Json.format[IdOfTodo]
}
case class DoneOfTodo(done: Boolean)
object DoneOfTodo {
  implicit val todoFormat = Json.format[DoneOfTodo]
}