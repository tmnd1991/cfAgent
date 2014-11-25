package it.unibo.ing.smacs.monit.parsers

/**
 * @author Antonio Murgia
 *         trait that defines parseOption and forces the developer to implement the parse method.
 *         T is the returned type and K is the input parameter type
 */
trait Parser[T,K] {
  def parse(x : K) : T
  def parseOption(x : K) = {
    try{
      Some(parse(x))
    }
    catch{
      case t : Throwable => None
    }
  }
}
