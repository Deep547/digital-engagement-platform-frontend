/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import config.AppConfig
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.mvc.{Cookie, MessagesControllerComponents}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.NuanceEncryptionService
import views.html._

class PaymentProblemsControllerSpec
    extends WordSpec
    with Matchers
    with GuiceOneAppPerSuite
    with ScalaCheckPropertyChecks {

  implicit private val fakeRequest = FakeRequest("GET", "/").withCookies(Cookie("mdtp", "12345"))

  implicit val appConfig = app.injector.instanceOf[AppConfig]
  val paymentProblemsView = app.injector.instanceOf[PaymentProblemsView]
  val nuanceEncryptionService = app.injector.instanceOf[NuanceEncryptionService]

  val messagesCC = app.injector.instanceOf[MessagesControllerComponents]

  private val controller = new PaymentProblemsController(
    appConfig,
    messagesCC,
    paymentProblemsView,
    nuanceEncryptionService)

  def asDocument(html: String): Document = Jsoup.parse(html)

  "fixed URLs" should {
    "render payment problems page" in {
      val result = controller.paymentProblem(fakeRequest)
      val doc = asDocument(contentAsString(result))

      status(result) shouldBe OK
      doc.select("h1").text() shouldBe "Coronavirus (COVID-19): tax support for businesses and self-employed"
    }
  }
}
