name := "g8-test-build"

resolvers += Classpaths.sbtPluginReleases
resolvers += Resolver.sonatypeRepo( "releases" )

addSbtPlugin( "org.scoverage"    % "sbt-scoverage"       % "1.5.1" )
addSbtPlugin( "com.eed3si9n"     % "sbt-buildinfo"       % "0.9.0" )
addSbtPlugin( "com.typesafe.sbt" % "sbt-native-packager" % "1.3.12" )
addSbtPlugin( "com.geirsson"     % "sbt-scalafmt"        % "1.5.1" )
