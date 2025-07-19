package $package$
package server

import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp

object Main extends IOApp:
  override def run( args: List[String] ): IO[ExitCode] =
    IO.println( s"\${$name;format="Camel"$.name} \${$name;format="Camel"$.version}" ) *>
      AppServer.resource[IO].use( _ => IO.pure( ExitCode.Success ) )
