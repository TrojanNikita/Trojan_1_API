package models
import play.api.data.validation.ValidationError
import play.api.libs.functional.syntax._
import play.api.libs.json._
case class Todo(id: Long, name: String, done:Boolean, priority:Int)

object Todo {

  implicit val jsonWrites = Json.writes[Todo]
  implicit val readsMyClass: Reads[Todo] = new Reads[Todo] {
    def reads(json: JsValue): JsResult[Todo] = (
      for {
        name <- (json \ "name").validate[String]
      } yield Todo(0,name,false,0)
    )
  }
}

