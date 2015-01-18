package it.unibo.ing.smacs.monit.parsers

import java.text.ParseException

import it.unibo.ing.smacs.monit.model.MonitResponseTime
import it.unibo.ing.utils.URLUtils

/**
 * @author Antonio Murgia
 *         parses a Seq[String] got from monit status command into a MonitResponseTime
 */
object ResponseTimeParser extends Parser[MonitResponseTime,Seq[String]]{
  val myDurationParser = DurationParser
  override def parse(seq: Seq[String]) = {
    val d = myDurationParser.parse(List(seq.head))
    val url = seq(2)
    val firstIndex = seq.indexWhere(_.startsWith("["))
    var mode = ""
    val range = firstIndex to seq.length-1
    for (i <- range){
      mode += seq(i)+ " "
    }
    //val mode = seq.filter(_.startsWith("[")).headOption
    MonitResponseTime(d,url,mode.trim)
  }
}
