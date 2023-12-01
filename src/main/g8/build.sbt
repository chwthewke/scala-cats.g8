import sbt._
import sbt.Keys._

// format: off
ThisBuild / organization      := "$organization$"
ThisBuild / scalaOrganization := "org.scala-lang"
ThisBuild / scalaVersion      := "$scalaVersion$"
// TODO when I can make sense of lm-coursier
ThisBuild / conflictManager                        := ConflictManager.strict
ThisBuild / updateSbtClassifiers / conflictManager := ConflictManager.default
// format: on

enablePlugins( FormatPlugin, DependenciesPlugin )

val `$name;format="norm"$-core` = project
  .in( file( "core" ) )
  .settings( libraryDependencies ++= cats ++ catsEffect )
  .enablePlugins( SbtBuildInfoPlugin, ScalacPlugin )

val `$name;format="norm"$-app` = project
  .in( file( "app" ) )
  .settings( Compile / run / fork := true )
  .dependsOn( `$name;format="norm"$-core` )
  .enablePlugins( SbtBuildInfoPlugin, ScalacPlugin )

val `$name;format="norm"$-tests` = project
  .in( file( "tests" ) )
  .settings( libraryDependencies ++= scalatest ++ scalacheck )
  .dependsOn( `$name;format="norm"$-core`, `$name;format="norm"$-app` )
  .enablePlugins( ScalacPlugin )

val `$name;format="norm"$` = project
  .in( file( "." ) )
  .aggregate(
    `$name;format="norm"$-core`,
    `$name;format="norm"$-app`,
    `$name;format="norm"$-tests`
  )
