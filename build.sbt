name := """[AЕеЕкщофт1"""
organization := "ффф"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test

//добавляем play-json
//libraryDependencies += "com.typesafe.play" %% "play-json" % "2.7.3"

//для работы с БД
//libraryDependencies ++= Seq(
//  "org.reactivemongo" %% "play2-reactivemongo" % "0.19.3-play27"
//)


libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "4.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2"
)

libraryDependencies+="org.postgresql" % "postgresql" % "42.2.1"


libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.1"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "ффф.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "ффф.binders._"
