import sbt._
import sbt.Keys._
import com.typesafe.sbt.SbtScalariform._
import scalariform.formatter.preferences._

scalaOrganization in ThisBuild := "org.scala-lang"
scalaVersion      in ThisBuild := "$scalaVersion$"

val $name;format="camel"$ScalariformSettings = defaultScalariformSettings ++ Seq(
  ScalariformKeys.preferences := defaultPreferences
    .setPreference( AlignSingleLineCaseStatements, true )
    .setPreference( SpaceBeforeColon, true )
    .setPreference( SpacesWithinPatternBinders, true )
    .setPreference( SpaceInsideParentheses, true )
    .setPreference( DoubleIndentClassDeclaration, true )
)

val $name;format="camel"$ScalacOptions = Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:higherKinds",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xfuture",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ypartial-unification",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused-import",
  "-Ywarn-value-discard"
)

val $name;format="camel"$ConsoleScalacOptions = $name;format="camel"$ScalacOptions.filterNot( _ == "-Ywarn-unused-import" )

val $name;format="camel"$TestScalacOptions = $name;format="camel"$ScalacOptions.filterNot( _ == "-Ywarn-valueDiscard" )

val sharedSettings = Seq( organization := "$organization$" )

val $name;format="camel"$ScalacSettings = Seq(
  scalacOptions                       := $name;format="camel"$ScalacOptions,
  // Note: uncomment this when importing to IntelliJ IDEA
  // scalacOptions                       := $name;format="camel"$TestScalacOptions,
  scalacOptions            in Compile := $name;format="camel"$ScalacOptions,
  scalacOptions in console in Compile := $name;format="camel"$ConsoleScalacOptions,
  scalacOptions            in Test    := $name;format="camel"$TestScalacOptions,
  scalacOptions in console in Test    := $name;format="camel"$ConsoleScalacOptions )

val $name;format="camel"$Settings =
  sharedSettings ++ 
  Defaults.coreDefaultSettings ++
  $name;format="camel"$ScalariformSettings ++
  $name;format="camel"$ScalacSettings :+
  ( libraryDependencies ++= Dependencies.common ) :+
  ( testOptions in Test += Tests.Argument( TestFrameworks.ScalaTest, "-oDF" ) )

val `$module;format="norm"$` = project
  .settings( $name;format="camel"$Settings )
  .settings( SbtBuildInfo.buildSettings( "$module;format="Camel"$BuildInfo" ) )
  .settings( Console.coreImports.settings )

val `$name;format="norm"$` = project.in( file( "." ) )
  .settings( sharedSettings )
  .aggregate( `$module;format="norm"$` )
