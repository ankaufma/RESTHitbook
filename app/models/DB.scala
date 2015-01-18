package models

import sorm._

/**
 * Created by ankaufma on 13.01.2015.
 */
object DB extends Instance(entities = Seq(Entity[FelledTree]()), url = "jdbc:h2:mem:play")
