package controllers

import javax.inject._

import play.api.libs.json._
import daos.UsersDAO
import play.api._
import play.api.mvc._
import play.api.i18n._

import play.api.data.Form
import play.api.data.Forms.{  mapping, nonEmptyText }

import play.api.data.validation.ValidationError
import scala.concurrent.{ExecutionContext,Future}
import models.{User}


class AuthController @Inject()(usersDAO: UsersDAO,
                                 cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {


  def get = Action.async { implicit request =>
    usersDAO.list().map { user =>
      Ok(Json.toJson(user))
    }
  }



  def add= Action(parse.json(User.readsMyClass)).async {
		implicit request =>
			val body = request.body      
			usersDAO.create(body) map (newUser=>
				Ok(Json.toJson(newUser))
			)     
  }


  
  def updateUser(id:Long,name:String)=Action.async{ implicit request =>      
      usersDAO.update(id,name).map{t=>
        Ok(Json.toJson(id))  
      }
  }
  	def loginUser(username:String,password:String)=Action.async{ implicit request =>      
		usersDAO.findByUsername(username).map{t=>
			Ok(Json.toJson({"tokken"}))  
		}
	}	

//   def loginUser= Action(parse.json(User.readsMyClass)).async {
//     implicit request =>
//         val body = request.body      
//         usersDAO.findByUsername(body.username) map (newUser=>
//             Ok(Json.toJson(newUser))
//         )     
//   }


//   def signIn = Action(parse.json(User.readsMyClass)).async  { 
// 	  implicit request =>
// 	   	val body = request.body  	
//         usersDAO.findByUsername(body.username).flatMap {
//           case Some(user) => 
//             Ok(Json.toJson(user))}
        
//   }
//if (user.password != bosy.password) errorUserNotFound


}