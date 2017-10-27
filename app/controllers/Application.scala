package controllers

import play.api.mvc._

import com.softwaremill.macwire._

trait ApplicationModule {
  def cc: ControllerComponents

  lazy val applicationController: Application = wire[Application]
}

class Application (cc: ControllerComponents) extends AbstractController(cc) {
  def index = Action {
    Ok(views.html.index())
  }

  def echo = Action {
    request => Ok(request.headers.toString())
  }
}
