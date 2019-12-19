package models

import play.api.data.Form
import play.api.libs.json._

case class Todo(id: Long, name: String, done:Boolean, priority:Int)

object Todo {
  implicit val todoFormat = Json.format[Todo]
}

case class NameOfTodo(name: String)
object NameOfTodo {
  implicit val nameFormat = Json.format[NameOfTodo]
}
case class IdOfTodo(id: Long)
object IdOfTodo {
  implicit val idFormat = Json.format[IdOfTodo]
}
case class DoneOfTodo(done: Boolean)
object DoneOfTodo {
  implicit val doneFormat = Json.format[DoneOfTodo]
}

case class PriorityOfTodo(priority: Int)
// object PriorityOfTodo {
//   implicit val priorityFormat = Json.format[DoneOfTodo]
// }

object PriorityOfTodo {
  implicit val readsMyClass: Reads[PriorityOfTodo] = new Reads[PriorityOfTodo] {
    def reads(json: JsValue): JsResult[PriorityOfTodo] = {
      for {
        priority <- (json \ "priority").validate[Int]
      } yield PriorityOfTodo(priority)
    }
  }
}