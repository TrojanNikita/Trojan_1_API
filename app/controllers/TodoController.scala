package controllers

import javax.inject._

import play.api.libs.json._
import daos.TodosDAO
import play.api._
import play.api.mvc._
import play.api.i18n._

import play.api.data.Form
import play.api.data.Forms.{  mapping, nonEmptyText }

import play.api.data.validation.ValidationError
import scala.concurrent.{ExecutionContext,Future}
import models.{Todo}


class TodoController @Inject()(todosDAO: TodosDAO,
                                 cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {


  def get = Action.async { implicit request =>
    todosDAO.list().map { todo =>
      Ok(Json.toJson(todo))
    }
  }



  def add= Action(parse.json(Todo.readsMyClass)).async {
    implicit request =>
        val body = request.body      
        todosDAO.create(body) map (newTodo=>
            Ok(Json.toJson(newTodo))
        )     
  }


  
  def updateTodo(id:Long,name:String)=Action.async{ implicit request =>      
      todosDAO.update(id,name).map{t=>
        Ok(Json.toJson(id))  
      }
  }

  def updateTodoDone(id:Long,status:Boolean)=Action.async { implicit request => 
    todosDAO.updateDone(id,status) map {_=>
       Ok(Json.toJson({id}))   
    }
  }


  def updateAllDone(status:Boolean)=Action.async { implicit request =>
    todosDAO.updateAllDone(status).map{_=>
    Ok(Json.toJson(status))   
    }
  }

  def updateTodoPriority(id:Long,priority:Int)=Action.async { implicit request =>
    todosDAO.updatePriority(id,priority).map{_=>
        Ok(Json.toJson(priority))   
    }
  }



  def deleteTodo(id:Long)=Action.async { implicit request =>
    todosDAO.delete(id).map{_=>
      Ok(Json.toJson(id))  
    }
  }

  def deleteAllDone(status:Boolean) = Action.async { implicit request => 
    todosDAO.deleteAll(status).map{t=>
      Ok(Json.toJson(status)) }
  }
}