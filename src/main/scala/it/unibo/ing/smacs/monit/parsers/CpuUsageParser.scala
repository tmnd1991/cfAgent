package it.unibo.ing.smacs.monit.parsers

import it.unibo.ing.smacs.monit.model.CpuUsage
import it.unibo.ing.smacs.monit.PimpMyLib._
/**
 * @author Antonio Murgia
 *         parses a Seq[String] got from monit status command into a CpuUsage
 */
object CpuUsageParser extends Parser[CpuUsage,Seq[String]]{
  override def parse(seq: Seq[String]) = {
    CpuUsage(seq.head.mySubstring(1,-3).toDouble,
             seq.tail.head.mySubstring(1,-3).toDouble,
             seq.tail.tail.head.mySubstring(1,-3).toDouble)
  }
}
