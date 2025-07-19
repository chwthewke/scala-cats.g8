import sbt._
import sbt.Keys._
import sbtcrossproject.CrossProject

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

val `$name;format="norm"$-core-cross`: CrossProject =
  crossProject( JSPlatform, JVMPlatform )
    .crossType( CrossType.Pure )
    .in( file( "core" ) )
    .settings( sharedSettings )
    .settings( cats, kittens, mouse, catsTime, circe )
    .enablePlugins( Scalac )

val `$name;format="norm"$-core-js`: Project  = `$name;format="norm"$-core-cross`.js
val `$name;format="norm"$-core-jvm`: Project = `$name;format="norm"$-core-cross`.jvm

val `$name;format="norm"$-core`: Project =
  project
    .in( file( "core/target" ) )
    .settings( sharedSettings )
    .settings( aggregateSettings )
    .aggregate( `$name;format="norm"$-core-js`, `$name;format="norm"$-core-jvm` )

val `$name;format="norm"$-assets-cross`: CrossProject =
  crossProject( JSPlatform, JVMPlatform )
    .crossType( CrossType.Pure )
    .in( file( "assets" ) )
    .settings( sharedSettings )
    .enablePlugins( Scalac )

val `$name;format="norm"$-assets-jvm`: Project = `$name;format="norm"$-assets-cross`.jvm
val `$name;format="norm"$-assets-js`: Project  = `$name;format="norm"$-assets-cross`.js

val `$name;format="norm"$-assets`: Project =
  project
    .in( file( "assets/target" ) )
    .settings( sharedSettings )
    .settings( aggregateSettings )
    .aggregate( `$name;format="norm"$-assets-js`, `$name;format="norm"$-assets-jvm` )

val `$name;format="norm"$-server`: Project = project
  .in( file( "server" ) )
  .enablePlugins( Scalac )
  .enablePlugins( BuildInfo )
  .settings( buildInfoPackage := "$package$.server" )
  .settings( sharedSettings )
  .dependsOn( `$name;format="norm"$-core-jvm` )
  .settings(
    circeParser,
    http4sCore,
    http4sDsl,
    http4sEmberServer,
    http4sCirce,
    scalatags,
    http4sScalatags,
    pureconfig,
    pureconfigCatsEffect,
    pureconfigFs2,
    pureconfigIp4s,
    pureconfigHttp4s,
    logging
  )

val backendRunnerSettings: Seq[Def.Setting[_]] = Seq(
  Compile / mainClass  := Some( "$package$.server.Main" ),
  Compile / run / fork := true
)

// NOTE this module is intended for running the backend from sbt or IntelliJ
//  it could have specific application.conf/logback.xml/assets etc.,
//  matching the requirements for $name;format="norm"$-frontend-run
val `$name;format="norm"$-server-run`: Project =
  project
    .in( file( "server-run" ) )
    .enablePlugins( Scalac )
    .settings( sharedSettings )
    .settings( backendRunnerSettings )
    .dependsOn( `$name;format="norm"$-server` )

val `$name;format="norm"$-frontend`: Project =
  project
    .in( file( "frontend" ) )
    .enablePlugins( Scalac )
    .enablePlugins( ScalaJSPlugin )
    .enablePlugins( BuildInfo )
    .settings( buildInfoPackage := "$package$.spa" )
    .settings( sharedSettings )
    .settings( scalaJSLinkerConfig ~= { _.withModuleKind( ModuleKind.ESModule ) } )
    .settings( tyrian, http4sCore, http4sDom, http4sCirce )
    .dependsOn( `$name;format="norm"$-core-js`, `$name;format="norm"$-assets-js` )

// NOTE this module is intended for running the frontend from sbt or a terminal
//  with a hot-reload capable dev webserver (via npm scripts using parcel)
val `$name;format="norm"$-frontend-run`: Project = project
  .in( file( "frontend-run" ) )
  .enablePlugins( Scalac )
  .enablePlugins( ScalaJSPlugin )
  .settings( sharedSettings )
  .settings(
    ideExcludedDirectories ++=
      Seq( ".parcel-cache", "dist", "node_modules" ).map( n => baseDirectory.value / n )
  )
  .settings( scalaJSLinkerConfig ~= { _.withModuleKind( ModuleKind.ESModule ) } )
  .settings( circeParser )
  .enablePlugins( FrontendDev )
  .dependsOn( `$name;format="norm"$-frontend` )

// NOTE this module is the "production" app, which contains the server and serves the frontend assets
val `$name;format="norm"$-app`: Project =
  project
    .in( file( "app" ) )
    .enablePlugins( Scalac )
    .enablePlugins( JavaServerAppPackaging )
    .enablePlugins( LauncherJarPlugin )
    .settings( sharedSettings )
    .settings( backendRunnerSettings )
    .settings( Packaging.settings( frontendProject = `$name;format="norm"$-frontend` ) )
    .dependsOn( `$name;format="norm"$-server` )

val `$name;format="norm"$-tests` = project
  .in( file( "tests" ) )
  .settings( sharedSettings )
  .settings( scalatest, scalacheck )
  .dependsOn( `$name;format="norm"$-core-jvm`, `$name;format="norm"$-server` )
  .enablePlugins( Scalac )

val `$name;format="norm"$` = project
  .in( file( "." ) )
  .settings( sharedSettings, aggregateSettings )
  .aggregate(
    `$name;format="norm"$-core`,
    `$name;format="norm"$-assets`,
    `$name;format="norm"$-server`,
    `$name;format="norm"$-server-run`,
    `$name;format="norm"$-frontend`,
    `$name;format="norm"$-frontend-run`,
    `$name;format="norm"$-app`,
    `$name;format="norm"$-tests`
  )
