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
import models.{Todo, NameOfTodo, IdOfTodo}
import models.DoneOfTodo

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

class HomeController @Inject()(repo: TodosDAO,
                                 cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {






  /**
   * A REST endpoint that gets all the people as JSON.
   */
  def getTodos = Action.async { implicit request =>
    repo.list().map { todo =>
      Ok(Json.toJson(todo))
    }
  }


    //Добавление 
  //Параметры:  name
  //       /todos/new    - routing
  def addTodo = Action(parse.tolerantJson).async { implicit request =>

      val body = request.body.as[NameOfTodo]
      
      repo.create(body.name,false,0).map(i => Ok(Json.toJson(i)))
      }   
   



    //Получение 
  //       /todos/id    - routing
  def getTodo(id:Long) = Action.async { implicit request =>
    repo.findById(id).map{t=>
      Ok(Json.toJson(t))}
  }


  //Изменение label
  //Параметры:  name
  //       /todo/id    - routing
  def updateTodo(id:Long)=Action(parse.tolerantJson) { implicit request =>

      val body = request.body.as[NameOfTodo]
      
      repo.update(id,body.name)
      Ok(Json.toJson({id}))  
  }


  //Изменение label
  //Параметры:  name
  //       /todo/id/change    - routing
  def updateTodoDone(id:Long)=Action(parse.tolerantJson) { implicit request =>

    val body = request.body.as[DoneOfTodo]    
    repo.updateDone(id,body.done)
    Ok(Json.toJson({id}))   
}





    //Удаление 
    //Параметры id удаляемого элемента
  //       /todos/id    - routing
    def deleteTodo=Action(parse.tolerantJson) { implicit request =>

      val body = request.body.as[IdOfTodo]      
      repo.delete(body.id)
      Ok(Json.toJson(body.id))   

  }

      //Удаление по выполненному доделать
  //       /todos/delete   - routing
  // def deleteAllDone = Action.async { implicit request =>
  //   repo.deleteAll(true)

  // }



}














// case class CreateTodoForm(name: String)