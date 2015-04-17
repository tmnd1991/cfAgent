package it.unibo.ing.smacs.monit.gatherer

import it.unibo.ing.smacs.monit.model.MonitInfo
import it.unibo.ing.utils.MostRecentKvalues

/**
 * Created by tmnd91 on 18/04/15.
 */
object RecentBuffer extends MostRecentKvalues[Seq[MonitInfo]](150)