import sbt._
import sbt.Keys._
import sbt.librarymanagement.DependencyBuilders

object DependenciesPlugin extends AutoPlugin {
  type Deps = Seq[ModuleID]

  object autoImport {
    type Deps = DependenciesPlugin.Deps

    implicit def ToStringOps( orgName: String ): StringOps = new StringOps( orgName )

    implicit def ToDbOanOps( dbOans: Seq[DbOan] ): DbOanOps = new DbOanOps( dbOans )

    implicit def ToGroupOps( deps: Deps ): GroupOps = new GroupOps( deps )

    val kindProjector: Deps =
      Seq( compilerPlugin( "org.typelevel" %% "kind-projector" % "0.11.3" cross CrossVersion.full ) )

    val splain: Deps = Seq( compilerPlugin( "io.tryp" % "splain" % "0.5.8" cross CrossVersion.patch ) )

    val betterMonadicFor: Deps = Seq( compilerPlugin( "com.olegpy" %% "better-monadic-for" % "0.3.1" ) )

    val silencerVersion = "1.6.0"
    val silencer: Deps = Seq(
      compilerPlugin( "com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full ),
      "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
    )

    val catsVersion         = "2.5.0"
    val cats: Deps          = "org.typelevel" %% Seq( "cats-core", "cats-kernel" ) % catsVersion
    val catsFree: Deps      = Seq( "org.typelevel" %% "cats-free" % catsVersion )
    val catsMtl: Deps       = Seq( "org.typelevel" %% "cats-mtl-core" % "0.7.1" )
    val mouse: Deps         = Seq( "org.typelevel" %% "mouse" % "1.0.0" )
    val kittens: Deps       = Seq( "org.typelevel" %% "kittens" % "2.2.1" )
    val alleycatsCore: Deps = Seq( "org.typelevel" %% "alleycats-core" % catsVersion )

    val catsEffect: Deps = Seq( "org.typelevel" %% "cats-effect" % "2.5.0" )

    val fs2: Deps = "co.fs2" %% Seq( "fs2-core", "fs2-io" ) % "2.5.4"

    val http4sVersion           = "0.21.22"
    val http4s: Deps            = Seq( "org.http4s" %% "http4s-dsl" % http4sVersion )
    val http4sBlazeServer: Deps = Seq( "org.http4s" %% "http4s-blaze-server" % http4sVersion )
    val http4sBlazeClient: Deps = Seq( "org.http4s" %% "http4s-blaze-client" % http4sVersion )

    val monocleVersion       = "2.0.4"
    val monocle: Deps        = "com.github.julien-truffaut" %% Seq( "monocle-core", "monocle-macro" ) % monocleVersion
    val monocleState: Deps   = Seq( "com.github.julien-truffaut" %% "monocle-state" % monocleVersion )
    val monocleGeneric: Deps = Seq( "com.github.julien-truffaut" %% "monocle-generic" % monocleVersion )

    val circeVersion      = "0.13.0"
    val circe: Deps       = "io.circe" %% Seq( "circe-core", "circe-generic", "circe-parser" ) % circeVersion
    val circeOptics: Deps = Seq( "io.circe" %% "circe-optics" % "0.13.0" )
    val circeFs2: Deps    = Seq( "io.circe" %% "circe-fs2" % "0.13.0" )
    val circeJawn: Deps   = Seq( "io.circe" %% "circe-jawn" % circeVersion )
    val jawnParser: Deps  = Seq( "org.typelevel" %% "jawn-parser" % "1.0.0" )

    val scodec: Deps = Seq(
      "org.scodec" %% "scodec-bits" % "1.1.24",
      "org.scodec" %% "scodec-core" % "1.11.7"
    )

    val atto: Deps = Seq( "org.tpolecat" %% "atto-core" % "0.9.2" )

    val asciiGraphs: Deps = Seq( "org.scalameta" %% "ascii-graphs" % "0.1.2" )
    val graphs: Deps      = "com.flowtick" %% Seq( "graphs-core", "graphs-cats" ) % "0.5.0"

    val spire: Deps    = Seq( "org.typelevel" %% "spire"         % "0.17.0-M1" )
    val algebird: Deps = Seq( "com.twitter"   %% "algebird-core" % "0.13.6" )
    val algebra: Deps  = Seq( "org.typelevel" %% "algebra"       % "2.0.0" )

    val breeze: Deps = "org.scalanlp" %% Seq( "breeze", "breeze-natives" ) % "1.1"
    val breezeDependencyOverrides: Deps =
      Seq(
        "org.apache.commons"       % "commons-math3" % "3.5",
        "com.github.fommil.netlib" % "core"          % "1.1.2"
      )

    val ojAlgo: Deps = Seq( "org.ojalgo" % "ojalgo" % "48.4.1" )

    val enumeratum: Deps =
      Seq( "com.beachape" %% "enumeratum" % "1.6.1", "com.beachape" %% "enumeratum-cats" % "1.6.1" )
    val enumeratumCirce: Deps = Seq( "com.beachape" %% "enumeratum-circe" % "1.6.1" )

    val shapeless: Deps = Seq( "com.chuusai" %% "shapeless" % "2.3.3" )

    val java8compat: Deps = Seq( "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.1" )
    val scalaXml: Deps    = Seq( "org.scala-lang.modules" %% "scala-xml"          % "1.3.0" )

    val logging: Deps =
      Seq(
        "org.slf4j"      % "slf4j-api"       % "1.7.30",
        "ch.qos.logback" % "logback-classic" % "1.2.3",
        "org.typelevel"  %% "log4cats-slf4j" % "1.2.2"
      )

    val pureconfigVersion = "0.14.1"
    val pureconfig: Deps = "com.github.pureconfig" %% Seq(
      "pureconfig-core",
      "pureconfig-cats",
      "pureconfig-generic"
    ) % pureconfigVersion

    val pureconfigEnumeratum: Deps = Seq( "com.github.pureconfig" %% "pureconfig-enumeratum"  % pureconfigVersion )
    val pureconfigCatsEffect: Deps = Seq( "com.github.pureconfig" %% "pureconfig-cats-effect" % pureconfigVersion )
    val pureconfigFs2: Deps        = Seq( "com.github.pureconfig" %% "pureconfig-fs2"         % pureconfigVersion )
    val pureconfigHttp4s: Deps     = Seq( "com.github.pureconfig" %% "pureconfig-http4s"      % pureconfigVersion )

    private[DependenciesPlugin] val typesafeConfig: Deps = Seq( "com.typesafe" % "config" % "1.4.0" )

    val decline: Deps = "com.monovore" %% Seq( "decline", "decline-effect" ) % "1.0.0"

    val doobieVersion             = "0.8.8"
    val doobie: Deps              = "org.tpolecat" %% Seq( "doobie-core", "doobie-free" ) % doobieVersion
    val doobiePostgres: Deps      = "org.tpolecat" %% Seq( "doobie-postgres", "doobie-hikari" ) % doobieVersion
    val doobiePostgresCirce: Deps = Seq( "org.tpolecat" %% "doobie-postgres-circe" % doobieVersion )
    val doobieH2: Deps            = Seq( "org.tpolecat" %% "doobie-h2" % doobieVersion )
    val doobieScalatest: Deps     = Seq( "org.tpolecat" %% "doobie-scalatest" % doobieVersion )

    val postgresql: Deps = Seq( "org.postgresql" % "postgresql"  % "42.2.12" )
    val h2database: Deps = Seq( "com.h2database" % "h2"          % "1.4.200" )
    val flywayCore: Deps = Seq( "org.flywaydb"   % "flyway-core" % "6.3.2" )

    val scalatest: Deps = Seq(
      "org.scalatest"     %% "scalatest"       % "3.2.6",
      "org.scalatestplus" %% "scalacheck-1-15" % "3.2.6.0"
    )

    val scalacheck: Deps =
      Seq(
        "org.scalacheck"    %% "scalacheck"      % "1.15.3",
        "io.chrisdavenport" %% "cats-scalacheck" % "0.3.0"
      )

    val uTest: Deps = Seq( "com.lihaoyi" %% "utest" % "0.7.4" )

    val uTestRunner: Def.Setting[_] =
      testFrameworks += new TestFramework( "utest.runner.Framework" )

    val autoDiffVersion          = "0.5.1"
    val autoDiff: Deps           = "fr.thomasdufour" %% Seq( "auto-diff-core", "auto-diff-generic" ) % autoDiffVersion
    val autoDiffEnumeratum: Deps = Seq( "fr.thomasdufour" %% "auto-diff-enumeratum" % autoDiffVersion )
    val autoDiffScalatest: Deps  = Seq( "fr.thomasdufour" %% "auto-diff-scalatest" % autoDiffVersion )
  }

  import autoImport._

  def allModules: Deps =
    cats ++
      catsFree ++
      catsMtl ++
      catsEffect ++
      mouse ++
      kittens ++
      alleycatsCore ++
      fs2 ++
      http4s ++
      http4sBlazeServer ++
      http4sBlazeClient ++
      monocle ++
      monocleState ++
      monocleGeneric ++
      circe ++
      circeFs2 ++
      circeJawn ++
      circeOptics ++
      scodec ++
      atto ++
      asciiGraphs ++
      graphs ++
      algebra ++
      spire ++
      algebird ++
      breeze ++
      breezeDependencyOverrides ++
      ojAlgo ++
      enumeratum ++
      enumeratumCirce ++
      shapeless ++
      java8compat ++
      jawnParser ++
      scalaXml ++
      logging ++
      pureconfig ++
      pureconfigEnumeratum ++
      pureconfigFs2 ++
      pureconfigHttp4s ++
      typesafeConfig ++
      decline ++
      doobie ++
      doobiePostgres ++
      doobiePostgresCirce ++
      doobieH2 ++
      doobieScalatest ++
      postgresql ++
      h2database ++
      flywayCore ++
      scalatest ++
      scalacheck ++
      uTest ++
      autoDiff ++
      autoDiffEnumeratum ++
      autoDiffScalatest

  override def buildSettings: Seq[Def.Setting[_]] =
    dependencyOverrides in ThisBuild ++= Seq(
      "org.scala-lang" % "scala-library"  % scalaVersion.value,
      "org.scala-lang" % "scala-compiler" % scalaVersion.value,
      "org.scala-lang" % "scala-reflect"  % scalaVersion.value
    ) ++ allModules

  type DbOan = DependencyBuilders.OrganizationArtifactName

  class GroupOps( val self: Seq[ModuleID] ) extends AnyVal {
    def exclude( org: String, name: String ): Seq[ModuleID] =
      self.map( _.exclude( org, name ) )

    def %( configurations: String ): Seq[ModuleID] =
      self.map( _ % configurations )

    def classifier( c: String ): Seq[ModuleID] =
      self.map( _ classifier c )
  }

  class StringOps( val self: String ) extends AnyVal {
    def %%( artifactIds: Seq[String] ): Seq[DbOan] = artifactIds.map( self %% _ )
  }

  class DbOanOps( val self: Seq[DbOan] ) extends AnyVal {
    def %( revision: String ): Deps = self.map( _ % revision )
  }

}
