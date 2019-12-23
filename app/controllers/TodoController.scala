package controllers

import javax.inject._

import play.api.libs.json._
import daos.TodosDAO
import play.api._
import play.api.mvc._
import play.api.i18n._

import play.api.data.Form
import play.api.data.Forms.{  mapping, nonEmptyText }
import scala.concurrent.{ExecutionContext,Future}
import models.Todo


class TodoController @Inject()(todosDAO: TodosDAO,
                                 cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {


  def getTodos = Action.async { implicit request =>
    todosDAO.list().map { todo =>
      Ok(Json.toJson(todo))
    }
  }



  def addTodo: Action[JsValue] = Action(parse.json) {
    implicit request =>
      val eitherTodo = validateParsedResult(request.body.validate[Todo])
      eitherTodo.fold(
        errorResponse => BadRequest(Json.toJson(errorResponse)),
        todo => todosDAO.create(todo).map(i => Ok(Json.toJson(i)))
      )
  }



  // def addTodo(name:String) = Action(parse.).async { implicit request =>


  //     todosDAO.create(name,false,0).map(i => Ok(Json.toJson(i)))
  //     }   
   

  def getTodo(id:Long) = Action.async { implicit request =>
    todosDAO.findById(id).map{t=>
      Ok(Json.toJson(t))}
  }

  def updateTodo(id:Long,name:String)=Action(parse.tolerantJson) { implicit request =>

      
      todosDAO.update(id,name)
      Ok(Json.toJson(id))  
  }

  def updateTodoDone(id:Long,status:Boolean)=Action(parse.tolerantJson) { implicit request =>
 
    todosDAO.updateDone(id,status)
    Ok(Json.toJson({id}))   
}


  def updateAllDone(status:Boolean)=Action(parse.tolerantJson) { implicit request =>

    todosDAO.updateAllDone(status)
    Ok(Json.toJson(status))   
}

  def updateTodoPriority(id:Long,priority:Int)=Action(parse.tolerantJson) { implicit request =>

    todosDAO.updatePriority(id,priority)
    Ok(Json.toJson(priority))   
}


  def deleteTodo(id:Long)=Action.async { implicit request =>

    todosDAO.delete(id).map{t=>
      Ok(Json.toJson(id))  
    }
  }

  def deleteAllDone(status:Boolean) = Action(parse.tolerantJson) { implicit request =>
 
    todosDAO.deleteAll(status)
      Ok(Json.toJson(status)) 
    

  }







  def validateParsedResult[T](jsResult: JsResult[T]): Either[ErrorResponse, T] =
  jsResult.fold(
    (errors: Seq[(JsPath, Seq[ValidationError])]) => {
      val map = fmtValidationResults(errors)
      Left(ErrorResponse("Validation Error", map))
    },
    (t: T) => Right(t)
  )
  




}
