// name := """[AЕеЕкщофт1"""
// organization := "ффф"

// version := "1.0-SNAPSHOT"

// lazy val root = (project in file(".")).enablePlugins(PlayScala)

// scalaVersion := "2.13.1"

// libraryDependencies += guice
// libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test


// libraryDependencies ++= Seq(
//   "com.typesafe.play" %% "play-slick" % "4.0.2",
//   "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2"

// )

// libraryDependencies+="mysql" % "mysql-connector-java" % "8.0.18"


// //libraryDependencies+="org.postgresql" % "postgresql" % "42.2.1"


// libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.1"


lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """[AЕеЕкщофт1""",
    version := "2.8.x",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      guice,
      "com.typesafe.play" %% "play-slick" % "5.0.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
      "mysql" % "mysql-connector-java" % "8.0.13",
      "com.typesafe.play" %% "play-json" % "2.8.1",
      specs2 % Test,
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )