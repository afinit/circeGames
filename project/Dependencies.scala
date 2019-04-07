import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  val circeVersion = "0.10.0"

  val backendDeps = Def.setting(
    Seq[ModuleID](
      "io.circe" %% "circe-core"      % circeVersion,
      "io.circe" %% "circe-generic"   % circeVersion,
      "io.circe" %% "circe-parser"    % circeVersion,

      "org.scalatest" %% "scalatest"  % "3.0.5" % Test
    )
  )


}
