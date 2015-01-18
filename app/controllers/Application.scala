package controllers

import com.fasterxml.jackson.databind.JsonNode
import models.{DB, FelledTree}
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats
import play.api.data.format.Formats._
import org.apache.commons.codec.binary.Base64._
import play.api.libs.json.Json
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  val felledTreeForm: Form[FelledTree] = Form {
    mapping(
      "lumberjack" -> text,
      "team" -> text,
      "areaDesctription" -> text,
      "longitude" -> text,
      "latitude" -> text,
      "height" -> text,
      "diamater" -> text,
      "date" -> text,
      "image" -> text
    )(FelledTree.apply)(FelledTree.unapply)
  }

  def addFelledTree = Action { implicit request =>
    val felledTree = felledTreeForm.bindFromRequest.get
    DB.save(felledTree)
    Redirect(routes.Application.index())
  }

  def addFelledTrees = Action(parse.json) { request =>
    val lj = (request.body \\ "lumberjack").map(_.as[String]).toList
    val team = (request.body \\ "team").map(_.as[String]).toList
    val areaDesctription = (request.body \\ "areaDescription").map(_.as[String]).toList
    val latitude = (request.body \\ "latitude").map(_.as[String]).toList
    val longitude = (request.body \\ "longitude").map(_.as[String]).toList
    val height = (request.body \\ "height").map(_.as[Double]).map(_.toString).toList
    val diamater = (request.body \\ "diameter").map(_.as[Double]).map(_.toString).toList
    val date = (request.body \\ "date").map(_.as[String]).toList
    val image = (request.body \\ "image").map(_.as[String]).toList
    var i = 0
    val alreadyFelledTrees = DB.query[FelledTree].fetch()
    lj.foreach { lj =>
      var toPersist = true
      alreadyFelledTrees.foreach { aft =>
        if (aft.date == date(i)) toPersist = false
      }
      if(toPersist) {
        DB.save(new FelledTree(lj, team(i), areaDesctription(i), latitude(i), longitude(i), height(i), diamater(i), date(i), image(i)))
        i = i + 1
      }
    }
    Ok("Thank you from RESTHitbook Server")
  }

  def getFelledTrees = Action {
    val felledTrees = DB.query[FelledTree].fetch()
    Ok(Json.toJson(felledTrees))
  }

  def getImage = Action {
    val felledTrees = DB.query[FelledTree].fetch()
    Ok(decodeBase64(felledTrees(0).image))as("image/jpg")
  }
}