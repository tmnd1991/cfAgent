package it.unibo.ing.smacs.monit.parsers

import java.text.ParseException
import java.util.concurrent.TimeUnit

import scala.concurrent.duration
import scala.concurrent.duration.{FiniteDuration, Duration}

/**
 * @author Antonio Murgia
 *         parses a Seq[String] got from monit status command into a scala.concurrent.Duration
 *         can parse these two formats :
 *         - 1d 1h 16m 4s
 *         - 0.012 s
 */
object DurationParser extends Parser[Duration,Seq[String]]{
  override def parse(seq : Seq[String]) = {
    var total = Duration.Zero
    for(ds <- seq){
      val d = extractDuration(ds)
      total += d
    }
    total
  }
  private def extractDuration(s : String) : FiniteDuration = {
    if (s.contains(".")){
      val num = (s.substring(0,s.length-1).toDouble * 1000).toInt
      if (s.last == 's')
        Duration(num,TimeUnit.MILLISECONDS)
      else
        throw new ParseException("unknown unit",0)
    }
    else{
      val num = s.substring(0,s.length-1).toInt
      s.last match{
        case 's' => Duration(num,TimeUnit.SECONDS)
        case 'm' => Duration(num,TimeUnit.MINUTES)
        case 'h' => Duration(num.toInt,TimeUnit.HOURS)
        case 'd' => Duration(num.toInt,TimeUnit.DAYS)
        case _ => throw new ParseException("unknown unit",0)
      }
    }
  }
}
