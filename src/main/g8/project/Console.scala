import sbt._
import sbt.Keys._

object Console {

  case class Imports( compileList : List[String], testList : List[String] ) {
    def depends( others : Imports* ) : Imports =
      Imports(
        others.foldRight( compileList )( _.compileList ++ _ ),
        others.foldRight( testList )( _.testList ++ _ ) )

    def compileS = compileList.map( "import " + _ ).mkString( "\n" )
    def testS = ( compileList ++ testList ).map( "import " + _ ).mkString( "\n" )

    def settings = Seq(
      initialCommands := compileS,
      initialCommands in Test := testS
    )
  }

  val coreImports = Imports(
    "$package$._" ::
      "cats._, data._, implicits._" ::
      Nil,
    "org.scalacheck.Gen, Gen._" ::
      Nil
  )
}
