package it.unibo.ing.smacs.monit

import it.unibo.ing.smacs.monit.model.MonitStatus
import it.unibo.ing.smacs.monit.parsers.ProcessParser
import org.scalatest.FlatSpec
import org.scalatest.Matchers

/**
 * Created by tmnd on 01/11/14.
 */
class ParsersTest extends FlatSpec with Matchers{
  "A text" should "be parse correctly" in{
    val s =
      """
        |Process 'nats'
        |  status                            running
        |  monitoring status                 monitored
        |  pid                               15982
        |  parent pid                        1
        |  uptime                            1h 3m
        |  children                          10
        |  memory kilobytes                  12188
        |  memory kilobytes total            56324
        |  memory percent                    0.5%
        |  memory percent total              2.7%
        |  cpu percent                       3.3%
        |  cpu percent total                 0.3%
        |  data collected                    Fri Oct 31 15:07:24 2014
        |  port response time                0.001s to 127.0.0.1:2345/ [HTTP via TCP]
        |  unix socket response time         0.000s to /var/vcap/data/warden/warden.sock [DEFAULT]
      """.stripMargin
    val x = ProcessParser.parse(s)
    x.status should be (MonitStatus.RUNNING)
    println(x)
  }
}
