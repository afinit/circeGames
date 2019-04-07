import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "com.mrka"
ThisBuild / organizationName := "mrka"

lazy val root = (project in file("."))
  .settings(
    name := "circeGames",
    libraryDependencies ++= Dependencies.backendDeps.value
  )

