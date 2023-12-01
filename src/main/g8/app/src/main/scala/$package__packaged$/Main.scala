package $package$

import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp

object Main extends IOApp {

  override def run( args: List[String] ): IO[ExitCode] =
    IO.println( s"\${buildinfo.$name;format="Camel"$.name} \${buildinfo.$name;format="Camel"$.version}" ) *>
      IO.println( s"The answer is \${Library.function}" ).as( ExitCode.Success )

}
