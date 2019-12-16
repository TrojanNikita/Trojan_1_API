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

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

class HomeController @Inject()(repo: TodosDAO,
                                 cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {




  /**
   * The mapping for the todo form.
   */
  // val todoForm: Form[CreateTodoForm] = Form {
  //   mapping(
  //     "name" -> nonEmptyText
  //   )(CreateTodoForm.apply)(CreateTodoForm.unapply)
  // }

  /**
   * The index action.
   */
  // def index = Action { implicit request =>
  //   Ok(views.html.index(todoForm))
  // }



  /**
   * The add todo action.
   *
   * This is asynchronous, since we're invoking the asynchronous methods on PersonRepository.
   */
  // def addTodo = Action(parse.json) { implicit request =>
  //   // // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
  //   // todoForm.bindFromRequest.fold(
  //   //   // The error function. We return the index page with the error form, which will render the errors.
  //   //   // We also wrap the result in a successful future, since this action is synchronous, but we're required to return
  //   //   // a future because the person creation function returns a future.
  //   //   errorForm => {
  //   //     Future.successful(Ok(views.html.index(errorForm)))
  //   //   },
  //   //   // There were no errors in the from, so create the todo.
  //   //   todo => {
  //   //     repo.create(todo.name).map { _ =>
  //   //       // If successful, we simply redirect to the index page.
  //   //       Redirect(routes.HomeController.index).flashing("success" -> "todo.created")
  //   //     }
  //   //   }
  //   // )
  //   val newTodo = request.body.as[String]

  //   val todos = repo.create(newTodo)
  //   Ok(Json.toJson(todos))


  // }


  def addTodo = Action(parse.tolerantJson) { implicit request =>


      val name = request.body.as[Todo]

      
      // Ok(Json.toJson(name))

      //val todos = repo.create(name)

      Ok(Json.toJson(name))      

    // val todos = repo.create(name)
    // Ok(Json.toJson(todos))


  }

  def getTodo(id:Long) = Action.async { implicit request =>
    repo.findById(id).map{t=>
      Ok(Json.toJson(t))}
  }



  /**
   * Handle the 'edit form' submission
   *
   * @param id Id of the computer to edit
   */
//  def updateTodo = Action.async { implicit request =>
//    // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
//    todoForm.bindFromRequest.fold(
//      // The error function. We return the index page with the error form, which will render the errors.
//      // We also wrap the result in a successful future, since this action is synchronous, but we're required to return
//      // a future because the person creation function returns a future.
//      errorForm => {
//        Future.successful(Ok(views.html.index(errorForm)))
//      },
//      // There were no errors in the from, so create the todo.
//      todo => {
//        repo.update(todo.name,todo).map { _ =>
//          // If successful, we simply redirect to the index page.
//          Redirect(routes.HomeController.index).flashing("success" -> "todo.created")
//        }
//      }
//    )
//  }




  /**
   * A REST endpoint that gets all the people as JSON.
   */
  def getTodos = Action.async { implicit request =>
    repo.list().map { todo =>
      Ok(Json.toJson(todo))
    }
  }

}

// case class CreateTodoForm(name: String)