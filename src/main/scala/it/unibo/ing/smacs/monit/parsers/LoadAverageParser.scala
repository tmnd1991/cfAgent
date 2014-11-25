package it.unibo.ing.smacs.monit.parsers

import it.unibo.ing.smacs.monit.model.LoadAverage
import it.unibo.ing.smacs.monit.PimpMyLib._
/**
 * @author Antonio Murgia
 *         parses a Seq[String] got from monit status command into a MonitProcessInfo
 */

object LoadAverageParser extends Parser[LoadAverage,Seq[String]]{
  override def parse(x: Seq[String]): LoadAverage ={
    LoadAverage(x.head.mySubstring(1,-1).toDouble,
                x.tail.head.mySubstring(1,-1).toDouble,
                x.tail.tail.head.mySubstring(1,-1).toDouble)
  }
}
