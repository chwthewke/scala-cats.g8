import sbt._
import sbt.Keys._

object Dependencies {
  def depsGroup(organization: String, version: String)(artifacts: String*)(testArtifacts: String*) =
    artifacts.map(organization       %% _ % version withSources ()) ++
      testArtifacts.map(organization %% _ % version % "test" withSources ())

  val kindProjector = compilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4")

  val catsVersion = "0.9.0"

  val cats = depsGroup("org.typelevel", catsVersion)("cats-core")("cats")

  val mouse = ("com.github.benhutchison" %% "mouse" % "0.9").withSources()

  val catsAll = (cats :+ mouse).map(_.exclude("org.scalacheck", "scalacheck_2.12"))

  val scalaArm = "com.jsuereth" %% "scala-arm" % "2.0" withSources ()

  val scalatest = "org.scalatest" %% "scalatest" % "3.0.3" withSources ()

  val scalacheck =
    Seq("org.scalacheck" %% "scalacheck" % "1.13.5",
      ("io.github.amrhassan" %% "scalacheck-cats" % "0.3.3").exclude("org.scalacheck", "scalacheck_2.12"))
      .map(_.withSources())

  val monocleVersion = "1.4.0"

  val monocle =
    depsGroup("com.github.julien-truffaut", monocleVersion)("monocle-core", "monocle-macro")()

  val circeVersion = "0.8.0"

  val circe =
    depsGroup("io.circe", circeVersion)("circe-core", "circe-generic", "circe-optics", "circe-parser")()

  val common = catsAll ++ scalacheck.map(_ % "test") ++ Seq(kindProjector, scalaArm, scalatest % "test")
}
