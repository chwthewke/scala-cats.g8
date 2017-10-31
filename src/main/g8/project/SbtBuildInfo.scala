import sbt._
import sbt.Keys._
import sbtbuildinfo._, BuildInfoKeys._
import sbtrelease.Version

object SbtBuildInfo {
  val shortVersion = SettingKey[String]( "short-version" )

  def buildSettings( objectName: String, targetPackage: String = "$package$" ) =
    BuildInfoPlugin.projectSettings ++ Seq(
      buildInfoPackage := targetPackage,
      buildInfoObject := objectName,
      shortVersion := shortenVersion( version.value ),
      buildInfoKeys := Seq( name, version, shortVersion, scalaVersion )
    )

  private def shortenVersion( version: String ): String = Version( version ) match {
    case Some( Version( major, subs, _ ) ) =>
      Version( major, subs.take( 1 ), None ).string
    case _ =>
      throw new IllegalArgumentException( s"Could not parse version \$version" )
  }
}
