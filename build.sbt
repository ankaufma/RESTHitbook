name := """RESTHitbook"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.sorm-framework" % "sorm" % "0.3.15",
  jdbc,
  anorm,
  cache,
  ws
)
