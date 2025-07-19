import sbt._
import sbt.Keys._

ThisBuild / organization := "$organization$"

ThisBuild / ideBasePackages.withRank( KeyRanks.Invisible ) := Seq( "$package$" )

ThisBuild / Compile / doc / sources                := Seq.empty
ThisBuild / Compile / packageDoc / publishArtifact := false

enablePlugins( Scalafmt )
enablePlugins( Dependencies )

val sharedSettings = Seq(
  scalaVersion                                          := "$scalaVersion$",
  ideExcludedDirectories.withRank( KeyRanks.Invisible ) := Seq( target.value )
)

val aggregateSettings = Seq(
  publish      := {},
  publishLocal := {}
)

val `$name;format="norm"$-core` = project
  .in( file( "core" ) )
  .settings( sharedSettings )
  .settings( cats, catsEffect )
  .enablePlugins( Scalac )

val `$name;format="norm"$-app` = project
  .in( file( "app" ) )
  .settings( sharedSettings )
  .settings( Compile / run / fork := true )
  .dependsOn( `$name;format="norm"$-core` )
  .enablePlugins( Scalac, BuildInfo )

val `$name;format="norm"$-tests` = project
  .in( file( "tests" ) )
  .settings( sharedSettings )
  .settings( scalatest, scalacheck )
  .dependsOn( `$name;format="norm"$-core`, `$name;format="norm"$-app` )
  .enablePlugins( Scalac )

val `$name;format="norm"$` = project
  .in( file( "." ) )
  .settings( sharedSettings, aggregateSettings )
  .aggregate(
    `$name;format="norm"$-core`,
    `$name;format="norm"$-app`,
    `$name;format="norm"$-tests`
  )
