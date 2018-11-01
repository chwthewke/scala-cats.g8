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

    val kindProjector: Deps = Seq( compilerPlugin( "org.spire-math" %% "kind-projector" % "0.9.8" ) )
    val splain: Deps        = Seq( compilerPlugin( "io.tryp"        % "splain"          % "0.3.4" cross CrossVersion.patch ) )

    val catsVersion    = "1.4.0"
    val cats: Deps     = "org.typelevel" %% Seq( "cats-core", "cats-kernel", "cats-macros" ) % catsVersion
    val catsFree: Deps = Seq( "org.typelevel" %% "cats-free" % catsVersion )
    val catsMtl: Deps  = Seq( "org.typelevel" %% "cats-mtl-core" % "0.4.0" )
    val mouse: Deps    = Seq( "org.typelevel" %% "mouse" % "0.19" )
    val kittens: Deps  = Seq( "org.typelevel" %% "kittens" % "1.2.0" )

    val catsEffect: Deps = Seq( "org.typelevel" %% "cats-effect" % "1.0.0" )

    val fs2: Deps = "co.fs2" %% Seq( "fs2-core", "fs2-io" ) % "1.0.0"

    val http4sVersion           = "0.20.0-M1"
    val http4s: Deps            = Seq( "org.http4s" %% "http4s-dsl" % http4sVersion )
    val http4sBlazeServer: Deps = Seq( "org.http4s" %% "http4s-blaze-server" % http4sVersion )
    val http4sBlazeClient: Deps = Seq( "org.http4s" %% "http4s-blaze-client" % http4sVersion )

    val monocleVersion     = "1.5.1-cats"
    val monocle: Deps      = "com.github.julien-truffaut" %% Seq( "monocle-core", "monocle-macro" ) % monocleVersion
    val monocleState: Deps = Seq( "com.github.julien-truffaut" %% "monocle-state" % monocleVersion )

    val circeVersion = "0.10.1"
    val circe: Deps  = "io.circe" %% Seq( "circe-core", "circe-generic", "circe-parser" ) % circeVersion
    val circeOptics  = Seq( "io.circe" %% "circe-optics" % "0.10.0" )

    val enumeratum: Deps =
      Seq( "com.beachape" %% "enumeratum" % "1.5.13", "com.beachape" %% "enumeratum-circe" % "1.5.18" )

    val shapeless: Deps = Seq( "com.chuusai" %% "shapeless" % "2.3.3" )

    val java8compat: Deps = Seq( "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.0" )
    val scalaLangModules: Deps =
      "org.scala-lang.modules" %% Seq( "scala-parser-combinators", "scala-xml" ) % "1.1.1"

    val logging: Deps = Seq( "org.slf4j" % "slf4j-api" % "1.7.25",
                            "ch.qos.logback"             % "logback-classic" % "1.2.3",
                            "com.typesafe.scala-logging" %% "scala-logging"  % "3.9.0" )

    val pureconfigVersion = "0.10.0"
    val pureconfig: Deps = "com.github.pureconfig" %% Seq( "pureconfig-core",
                                                          "pureconfig-cats",
                                                          "pureconfig-enumeratum",
                                                          "pureconfig-cats-effect" ) % pureconfigVersion

    val pureconfigFs2: Deps    = Seq( "com.github.pureconfig" %% "pureconfig-fs2"    % pureconfigVersion )
    val pureconfigHttp4s: Deps = Seq( "com.github.pureconfig" %% "pureconfig-http4s" % pureconfigVersion )

    private[DependenciesPlugin] val typesafeConfig: Deps = Seq( "com.typesafe" % "config" % "1.3.3" )

    val decline = Seq( "com.monovore" %% "decline" % "0.5.1" )

    val doobieVersion = "0.6.0"
    val doobie: Deps =
      "org.tpolecat" %% Seq( "doobie-core", "doobie-free" ) % doobieVersion
    val doobiePostgres
      : Deps            = "org.tpolecat" %% Seq( "doobie-postgres", "doobie-postgres-circe", "doobie-hikari" ) % doobieVersion
    val doobieH2        = Seq( "org.tpolecat" %% "doobie-h2" % doobieVersion )
    val doobieScalatest = Seq( "org.tpolecat" %% "doobie-scalatest" % doobieVersion )

    val postgresql: Deps = Seq( "org.postgresql" % "postgresql"  % "42.2.5" )
    val h2database: Deps = Seq( "com.h2database" % "h2"          % "1.4.197" )
    val flywayCore: Deps = Seq( "org.flywaydb"   % "flyway-core" % "5.2.1" )

    val scalatest: Deps = Seq( "org.scalatest" %% "scalatest" % "3.0.5" )

    val scalacheck: Deps =
      Seq(
        "org.scalacheck"      %% "scalacheck"      % "1.13.5",
        "io.github.amrhassan" %% "scalacheck-cats" % "0.4.0"
      )

    val autoDiff: Deps =
      "fr.thomasdufour" %% Seq( "auto-diff-core", "auto-diff-generic", "auto-diff-scalatest", "auto-diff-enumeratum" ) % "0.2.0"

  }

  import autoImport._

  def allModules =
    cats ++
      catsFree ++
      catsMtl ++
      mouse ++
      catsEffect ++
      fs2 ++
      http4s ++
      http4sBlazeServer ++
      http4sBlazeClient ++
      kittens ++
      monocle ++
      monocleState ++
      circe ++
      circeOptics ++
      enumeratum ++
      shapeless ++
      java8compat ++
      scalaLangModules ++
      logging ++
      pureconfig ++
      pureconfigFs2 ++
      pureconfigHttp4s ++
      typesafeConfig ++
      decline ++
      doobie ++
      doobiePostgres ++
      doobieH2 ++
      postgresql ++
      h2database ++
      flywayCore ++
      scalatest ++
      scalacheck ++
      autoDiff

  override def buildSettings: Seq[Def.Setting[_]] =
    dependencyOverrides in ThisBuild ++= Seq(
      "org.scala-lang" % "scala-library" % scalaVersion.value,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
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
