import controllers.{ ApplicationModule, AssetsComponents }
import router.Routes

import play.api._
import play.filters.HttpFiltersComponents

import com.softwaremill.macwire._

class AppLoader
    extends ApplicationLoader
{
  import play.api.ApplicationLoader.Context

  override def load(context: Context) =
  {
    LoggerConfigurator(context.environment.classLoader) foreach
    {
      _.configure(context.environment, context.initialConfiguration, Map.empty)
    }

    (new BuiltInComponentsFromContext(context) with AppComponent).application
  }
}

trait AppComponent
    extends BuiltInComponents
    with ControllersModule
    with HttpFiltersComponents
    with AssetsComponents
{
  import play.api.http.{ HttpErrorHandler }

  def httpErrorHandler: HttpErrorHandler

  lazy val prefix: String = "/"
  override lazy val router = wire[Routes]
}

trait ControllersModule
    extends ApplicationModule
{
  import play.api.mvc._

  def controllerComponents: ControllerComponents

  override lazy val cc: ControllerComponents = controllerComponents
}
