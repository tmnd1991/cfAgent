package it.unibo.ing.smacs.monit.parsers

/**
 * Created by tmnd on 01/11/14.
 */
import scala.concurrent.duration.Duration

trait IDurationParser {
  def parse(seq : Seq[String]) : Duration
  def parseOption(seq : Seq[String]) = {
    try Some(parse(seq))
    catch{
      case t : Throwable => None
    }
  }
}
