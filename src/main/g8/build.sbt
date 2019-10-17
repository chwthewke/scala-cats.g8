import sbt._
import sbt.Keys._

// format: off
organization      in ThisBuild := "$organization$"
scalaOrganization in ThisBuild := "org.scala-lang"
scalaVersion      in ThisBuild := "$scalaVersion$"
// TODO when I can make sense of lm-coursier
conflictManager   in ThisBuild                         := ConflictManager.strict
conflictManager   in updateSbtClassifiers in ThisBuild := ConflictManager.default
// format: on

enablePlugins( FormatPlugin, DependenciesPlugin )

val `$module;format="norm"$` = project
  .settings( libraryDependencies ++= cats ++ catsEffect )
  .enablePlugins( SbtBuildInfo, ScalacPlugin )

val `$name;format="norm"$-tests` = project
  .settings( libraryDependencies ++= (scalatest ++ scalacheck).map( _ % "test" ) )
  .dependsOn( `$module;format="norm"$` )
  .enablePlugins( ScalacPlugin )

val `$name;format="norm"$-all` = project
  .in( file( "." ) )
  .aggregate(
    `$module;format="norm"$`,
    `$name;format="norm"$-tests`
  )
