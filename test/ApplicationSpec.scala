import org.scalatest.concurrent._

import org.scalatestplus.play._
import play.api.http._
import play.api.mvc.AnyContentAsEmpty
import play.api.test.{ FakeRequest, FakeHeaders }
import play.api.test.Helpers._

class ApplicationControllerSpec extends PlaySpec
    with BaseOneAppPerTest
    with AppFactory
    with ScalaFutures
{
  "ApplicationController" should {
    "render index page at /" in {
      lazy val headers =
        new FakeHeaders(Seq(
          HeaderNames.HOST -> "localhost:9000"
        ))
      lazy val index =
        route(app, FakeRequest(GET, "/", headers, AnyContentAsEmpty)).get

      status(index) mustBe Status.OK
      contentType(index) mustBe Some("text/html")
      contentAsString(index) must include ("Hello")
    }

    "returns headers at /echo" in {
      lazy val headers =
        new FakeHeaders(Seq(
          "hi" -> "there",
          HeaderNames.HOST -> "localhost:9000"
        ))
      lazy val echo =
        route(app, FakeRequest(GET, "/echo", headers, AnyContentAsEmpty)).get

      status(echo) mustBe Status.OK
      contentType(echo) mustBe Some("text/plain")
      contentAsString(echo) must include (headers.toString().drop(5).init)
    }
  }
}
