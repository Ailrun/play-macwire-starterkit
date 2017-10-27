import controllers.{ ApplicationModule, Assets, AssetsComponents }
import router.Routes

import play.api._
import play.api.mvc._
import play.api.http.{ HttpErrorHandler }
import play.api.ApplicationLoader.Context
import play.filters.HttpFiltersComponents

import com.softwaremill.macwire._

class AppLoader extends ApplicationLoader
{
  override def load(context: Context) =
  {
    LoggerConfigurator(context.environment.classLoader) foreach
    {
      _.configure(context.environment, context.initialConfiguration, Map.empty)
    }

    (new BuiltInComponentsFromContext(context) with AppComponents).application
  }
}

trait AppComponents
    extends BuiltInComponents
    with ControllersModule
    with HttpFiltersComponents
    with AssetsComponents
{
  def httpErrorHandler: HttpErrorHandler
  def assets: Assets

  lazy val prefix: String = "/"
  override lazy val router = wire[Routes]
}

trait ControllersModule extends ApplicationModule {
  def controllerComponents: ControllerComponents

  override lazy val cc: ControllerComponents = controllerComponents
}
