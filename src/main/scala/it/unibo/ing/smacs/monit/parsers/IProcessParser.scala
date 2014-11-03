package it.unibo.ing.smacs.monit.parsers

import it.unibo.ing.smacs.monit.model.MonitProcessInfo

/**
 * Created by tmnd on 31/10/14.
 */
trait IProcessParser {
  def parse(s : String) : MonitProcessInfo
}
