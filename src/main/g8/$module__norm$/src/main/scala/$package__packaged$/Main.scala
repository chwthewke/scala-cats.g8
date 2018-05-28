package $package$

object Main {
  def main( args: Array[String] ) = println( s"\${buildinfo.$name;format="Camel"$.name} \${buildinfo.$name;format="Camel"$.version}" )
}
