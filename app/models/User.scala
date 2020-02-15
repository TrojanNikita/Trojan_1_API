package models
import play.api.data.validation.ValidationError
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class User(
  id: Long,
  username: String,
  password: String,
  email: String
)


object User {

  implicit val jsonWrites = Json.writes[User]
  implicit val readsMyClass: Reads[User] = new Reads[User] {
    def reads(json: JsValue): JsResult[User] = (
      for {
		username <- (json \ "username").validate[String]  
		password <- (json \ "password").validate[String]
		email <- (json \ "email").validate[String]
      } yield User(0,username,password,email)
    )
  }
}



// object Subscription extends SubscriptionJson {
//   def getFromRequest(request: RequestHeader) = {
//     val actions = request.getQueryString("actions").map(_.split(",").toSet).getOrElse(Set.empty[String])
//     val cinemaIdO = request.cookies.get("currentCinema").flatMap(_.value.toIntO)

//     Subscription(actions, cinemaIdO)
//   }
// }