package models

import play.api.data.Form
import play.api.libs.json._

case class Todo(id: Long, name: String)

object Todo {
  implicit val todoFormat = Json.format[Todo]
}


