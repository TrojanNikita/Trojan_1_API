package models

import play.api.libs.json._
import play.api.data.Form
case class Todo(id: Long, name: String, done:Boolean, priority:Int)

// object Todo {
//       implicit val todoFormat = Json.format[Todo]
//}

object Todo {
  // #EasyWayOut
  // implicit val jsonFormat = Json.format[Person]

  implicit val jsonWrites = Json.writes[Todo]
  implicit val jsonValidatedReads = (
    (JsPath \ "id").read[Long] and  //vanilla read followed by additional validators
 //     .filter(ValidationError("must be more than 2 characters"))(fname => fname.length > 2) and

      (JsPath \ "name").read[String] and
  //      .filter(ValidationError("must be more than 2 characters"))(lname => lname.length > 2) and

      (JsPath \ "done").read[Boolean] and
    //    .filter(ValidationError("must be 10 digits"))(number => number.length == 10)
    //    .filter(ValidationError("must be a number"))(number => number.forall(Character.isDigit)) and

      (JsPath \ "priority").read[Int]

    ) (Todo.apply _)
}

