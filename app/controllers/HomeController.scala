package controllers

import javax.inject._

import play.api.libs.json.Json
import daos.TodosDAO
import play.api._
import play.api.mvc._
import play.api.i18n._

import play.api.data.Form
import play.api.data.Forms.{  mapping, nonEmptyText }
import scala.concurrent.{ExecutionContext,Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

class HomeController @Inject()(repo: TodosDAO,
                                 cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  /**
   * The mapping for the person form.
   */
  val todoForm: Form[CreateTodoForm] = Form {
    mapping(
      "name" -> nonEmptyText
    )(CreateTodoForm.apply)(CreateTodoForm.unapply)
  }

  /**
   * The index action.
   */
  def index = Action { implicit request =>
    Ok(views.html.index(todoForm))
  }



  /**
   * The add person action.
   *
   * This is asynchronous, since we're invoking the asynchronous methods on PersonRepository.
   */
  def addTodo = Action.async { implicit request =>
    // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
    todoForm.bindFromRequest.fold(
      // The error function. We return the index page with the error form, which will render the errors.
      // We also wrap the result in a successful future, since this action is synchronous, but we're required to return
      // a future because the person creation function returns a future.
      errorForm => {
        Future.successful(Ok(views.html.index(errorForm)))
      },
      // There were no errors in the from, so create the person.
      person => {
        repo.create(person.name).map { _ =>
          // If successful, we simply redirect to the index page.
          Redirect(routes.HomeController.index).flashing("success" -> "todo.created")
        }
      }
    )
  }





  /**
   * A REST endpoint that gets all the people as JSON.
   */
  def getTodos = Action.async { implicit request =>
    repo.list().map { todo =>
      Ok(Json.toJson(todo))
    }
  }

}

case class CreateTodoForm(name: String)