import sbt._
import sbt.Keys._

// format: off
scalaOrganization in ThisBuild := "org.scala-lang"
scalaVersion      in ThisBuild := "$scalaVersion$"
conflictManager   in ThisBuild := ConflictManager.strict
// format: on

val sharedSettings = Seq(organization := "$organization$")

val $name;format="camel"$Settings =
  Defaults.coreDefaultSettings ++
    sharedSettings ++
    Scalac.settings :+
    (libraryDependencies ++= Dependencies.common) :+
    (testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oDF"))

val `$module;format="norm"$` = project
  .settings($name;format="camel"$Settings)
  .settings(SbtBuildInfo.buildSettings("$module;format="Camel"$BuildInfo"))
  .settings(Console.coreImports.settings)

val `$name;format="norm"$` = project
  .in(file("."))
  .settings(sharedSettings)
  .aggregate(`$module;format="norm"$`)
