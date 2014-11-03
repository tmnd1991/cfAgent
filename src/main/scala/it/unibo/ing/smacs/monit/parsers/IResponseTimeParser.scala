package it.unibo.ing.smacs.monit.parsers

import it.unibo.ing.smacs.monit.model.MonitResponseTime

/**
 * Created by tmnd on 03/11/14.
 */
trait IResponseTimeParser {
  def parse(seq : Seq[String]) : MonitResponseTime
  def parseOption(seq : Seq[String]) = {
    try Some(parse(seq))
    catch{
      case t : Throwable => {
        println(t)
        None
      }
    }
  }
}
