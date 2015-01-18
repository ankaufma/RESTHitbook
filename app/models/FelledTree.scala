package models

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO


import org.omg.IOP.Codec
import play.api.libs.json.Json


/**
 * Created by ankaufma on 13.01.2015.
 */
case class FelledTree(lumberjack: String, team: String, areaDescription: String, latitude: String, longitude: String, height: String, diameter: String, date: String, image: String) {
  def calcVolume = (this.diameter.toFloat*this.diameter.toFloat)/40000*Math.PI*this.height.toFloat
}

object FelledTree{
  implicit val felledTreeFormat = Json.format[FelledTree]
}
