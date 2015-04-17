package it.unibo.ing.smacs.monit.gatherer

import it.unibo.ing.smacs.monit.model.MonitInfo
import it.unibo.ing.smacs.monit.parsers.MonitOutputParser
import it.unibo.ing.utils.BufferedLineIterator

/**
 * Created by tmnd91 on 17/04/15.
 */
object DataGatherer {
  private var _lastGather : Long = 0
  private var _lastData : Seq[MonitInfo] = _
  private val timeout : Long = 60000
  def getData() : Option[Seq[MonitInfo]] = {
    if (_lastGather + timeout < System.currentTimeMillis()){
      try {
        val p = Runtime.getRuntime().exec("/var/vcap/bosh/bin/monit status")
        val stdInput = new BufferedLineIterator(p.getInputStream)
        val stdError = new BufferedLineIterator(p.getErrorStream)
        val s = stdInput.mkString("\n")
        if (!stdError.isEmpty)
          None
        else {
          MonitOutputParser.parseOption(s) match {
            case Some(x: Seq[MonitInfo]) => {
              _lastGather = System.currentTimeMillis()
              _lastData = x
              Some(_lastData)
            }
            case None => None
          }
        }
      }
      catch{
        case t : Throwable => None
      }
    }
    else{
      Some(_lastData)
    }
  }

}
