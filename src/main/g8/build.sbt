import sbt._
import sbt.Keys._

// format: off
organization      in ThisBuild := "$organization$"
scalaOrganization in ThisBuild := "org.scala-lang"
scalaVersion      in ThisBuild := "$scalaVersion$"
conflictManager   in ThisBuild := ConflictManager.strict
// format: on

enablePlugins( FormatPlugin, DependenciesPlugin )

val `$module;format="norm"$` = project
  .settings( libraryDependencies ++= cats ++ scalatest % "test" )
  .enablePlugins( SbtBuildInfo, ScalacPlugin )

val `$name;format="norm"$` = project
  .in( file( "." ) )
  .aggregate( `$module;format="norm"$` )
