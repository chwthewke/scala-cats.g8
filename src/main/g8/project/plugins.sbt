name := "$name;format="norm"$-build"

resolvers += Classpaths.sbtPluginReleases
resolvers += Resolver.sonatypeRepo( "releases" )

dependencyOverrides += "org.scala-lang.modules" %% "scala-xml" % "2.2.0"

addSbtPlugin( "org.scoverage"    % "sbt-scoverage"       % "2.3.1" )
addSbtPlugin( "com.eed3si9n"     % "sbt-buildinfo"       % "0.13.1" )
addSbtPlugin( "com.github.sbt"   % "sbt-native-packager" % "1.11.1" )
addSbtPlugin( "org.scalameta"    % "sbt-scalafmt"        % "2.5.5" )
