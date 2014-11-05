package it.unibo.ing.smacs.monit

import java.io.{IOException, InputStreamReader, BufferedReader}

import it.unibo.ing.smacs.monit.model.JsonConversions._
import it.unibo.ing.smacs.monit.model.MonitStatus
import it.unibo.ing.smacs.monit.parsers.{MonitOutputParser, SystemParser, ProcessParser}
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import spray.json.DefaultJsonProtocol
import scala.collection.JavaConversions._

/**
 * Created by tmnd on 01/11/14.
 */
class ParsersTest extends FlatSpec with Matchers{
  "A text" should "be parsed into a Process correctly" in{
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

  "A text" should "be parsed into a System correctly" in{
    val s =
      """System 'system_lucid64'
        |  status                            running
        |  monitoring status                 monitored
        |  load average                      [0.14] [0.19] [0.27]
        |  cpu                               0.9%us 1.2%sy 0.0%wa
        |  memory usage                      965564 kB [46.9%]
        |  swap usage                        202768 kB [18.4%]
        |  data collected                    Fri Oct 31 11:25:21 2014
      """.stripMargin
    val x = SystemParser.parse(s)
    println(x)
  }

  "A bunch of text" should "be parsed in a list of infos" in{
    val s = """The Monit daemon 5.2.4 uptime: 1h 16m
              |
              |Process 'nats'
              |  status                            running
              |  pid                               3776
              |  uptime                            1h 15m
              |  children                          8
              |
              |  monitoring status                 monitored
              |  parent pid                        1
              |  memory percent                    0.0%
              |  memory kilobytes                  1864
              |  cpu percent                       0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |  memory kilobytes total            2852
              |  memory percent total              0.1%
              |  cpu percent total                 0.0%
              |
              |Process 'nats_stream_forwarder'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               3824
              |  parent pid                        1
              |  uptime                            1h 15m
              |  children                          8
              |  memory kilobytes                  13020
              |  memory kilobytes total            14000
              |  memory percent                    0.6%
              |  memory percent total              0.6%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'cloud_controller_ng'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               3869
              |  parent pid                        1
              |  uptime                            1h 15m
              |  children                          8
              |  memory kilobytes                  73556
              |  memory kilobytes total            74512
              |  memory percent                    3.5%
              |  memory percent total              3.6%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  port response time                0.003s to 10.39.39.39:9022/v2/info [HTTP via TCP]
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'cloud_controller_worker_local_1'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               3938
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          8
              |  memory kilobytes                  73464
              |  memory kilobytes total            74508
              |  memory percent                    3.5%
              |  memory percent total              3.6%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'cloud_controller_worker_local_2'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4001
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          8
              |  memory kilobytes                  103172
              |  memory kilobytes total            104204
              |  memory percent                    5.0%
              |  memory percent total              5.0%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'nginx_cc'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4081
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          1
              |  memory kilobytes                  24
              |  memory kilobytes total            780
              |  memory percent                    0.0%
              |  memory percent total              0.0%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'cloud_controller_worker_1'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4084
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          8
              |  memory kilobytes                  70516
              |  memory kilobytes total            71556
              |  memory percent                    3.4%
              |  memory percent total              3.4%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'cloud_controller_clock'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4135
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          8
              |  memory kilobytes                  32236
              |  memory kilobytes total            33244
              |  memory percent                    1.5%
              |  memory percent total              1.6%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'uaa'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4179
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          8
              |  memory kilobytes                  309488
              |  memory kilobytes total            310556
              |  memory percent                    15.0%
              |  memory percent total              15.1%
              |  cpu percent                       0.4%
              |  cpu percent total                 0.4%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'uaa_cf-registrar'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4244
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          8
              |  memory kilobytes                  16208
              |  memory kilobytes total            17204
              |  memory percent                    0.7%
              |  memory percent total              0.8%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'haproxy'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4288
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          0
              |  memory kilobytes                  1672
              |  memory kilobytes total            1672
              |  memory percent                    0.0%
              |  memory percent total              0.0%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'gorouter'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4291
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          8
              |  memory kilobytes                  5732
              |  memory kilobytes total            6712
              |  memory percent                    0.2%
              |  memory percent total              0.3%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'warden'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4395
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          8
              |  memory kilobytes                  24372
              |  memory kilobytes total            25348
              |  memory percent                    1.1%
              |  memory percent total              1.2%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  port response time                0.001s to 127.0.0.1:2345/ [HTTP via TCP]
              |  unix socket response time         0.000s to /var/vcap/data/warden/warden.sock [DEFAULT]
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'dea_next'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4600
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          8
              |  memory kilobytes                  40940
              |  memory kilobytes total            45648
              |  memory percent                    1.9%
              |  memory percent total              2.2%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'dir_server'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4642
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          8
              |  memory kilobytes                  1544
              |  memory kilobytes total            6260
              |  memory percent                    0.0%
              |  memory percent total              0.3%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'dea_logging_agent'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4684
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          2
              |  memory kilobytes                  2620
              |  memory kilobytes total            2620
              |  memory percent                    0.1%
              |  memory percent total              0.1%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'loggregator'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4727
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          2
              |  memory kilobytes                  5168
              |  memory kilobytes total            5168
              |  memory percent                    0.2%
              |  memory percent total              0.2%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'loggregator_trafficcontroller'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4779
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          2
              |  memory kilobytes                  3948
              |  memory kilobytes total            3948
              |  memory percent                    0.1%
              |  memory percent total              0.1%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'etcd'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4821
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          8
              |  memory kilobytes                  23356
              |  memory kilobytes total            28056
              |  memory percent                    1.1%
              |  memory percent total              1.3%
              |  cpu percent                       0.4%
              |  cpu percent total                 0.4%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'etcd_metrics_server'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4866
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          8
              |  memory kilobytes                  1624
              |  memory kilobytes total            6332
              |  memory percent                    0.0%
              |  memory percent total              0.3%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'hm9000_listener'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4894
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          2
              |  memory kilobytes                  4804
              |  memory kilobytes total            4804
              |  memory percent                    0.2%
              |  memory percent total              0.2%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'hm9000_fetcher'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4938
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          2
              |  memory kilobytes                  5724
              |  memory kilobytes total            5724
              |  memory percent                    0.2%
              |  memory percent total              0.2%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'hm9000_analyzer'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               4970
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          2
              |  memory kilobytes                  4868
              |  memory kilobytes total            4868
              |  memory percent                    0.2%
              |  memory percent total              0.2%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'hm9000_sender'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               5008
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          2
              |  memory kilobytes                  5136
              |  memory kilobytes total            5136
              |  memory percent                    0.2%
              |  memory percent total              0.2%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'hm9000_metrics_server'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               5041
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          2
              |  memory kilobytes                  3772
              |  memory kilobytes total            3772
              |  memory percent                    0.1%
              |  memory percent total              0.1%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'hm9000_api_server'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               5073
              |  parent pid                        1
              |  uptime                            1h 14m
              |  children                          2
              |  memory kilobytes                  2696
              |  memory kilobytes total            2696
              |  memory percent                    0.1%
              |  memory percent total              0.1%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'hm9000_evacuator'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               5104
              |  parent pid                        1
              |  uptime                            1h 13m
              |  children                          2
              |  memory kilobytes                  3524
              |  memory kilobytes total            3524
              |  memory percent                    0.1%
              |  memory percent total              0.1%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'hm9000_shredder'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               5136
              |  parent pid                        1
              |  uptime                            1h 13m
              |  children                          2
              |  memory kilobytes                  3632
              |  memory kilobytes total            3632
              |  memory percent                    0.1%
              |  memory percent total              0.1%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'metron_agent'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               5169
              |  parent pid                        1
              |  uptime                            1h 13m
              |  children                          2
              |  memory kilobytes                  5888
              |  memory kilobytes total            5888
              |  memory percent                    0.2%
              |  memory percent total              0.2%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.0%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |Process 'postgres'
              |  status                            running
              |  monitoring status                 monitored
              |  pid                               3748
              |  parent pid                        1
              |  uptime                            1h 16m
              |  children                          20
              |  memory kilobytes                  9208
              |  memory kilobytes total            84296
              |  memory percent                    0.4%
              |  memory percent total              4.0%
              |  cpu percent                       0.0%
              |  cpu percent total                 0.4%
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |System 'system_lucid64'
              |  status                            running
              |  monitoring status                 monitored
              |  load average                      [0.14] [0.19] [0.27]
              |  cpu                               0.9%us 1.2%sy 0.0%wa
              |  memory usage                      965564 kB [46.9%]
              |  swap usage                        202768 kB [18.4%]
              |  data collected                    Fri Oct 31 11:25:21 2014
              |
              |""".stripMargin
    val list = MonitOutputParser.parse(s)
    list.foreach(println)
  }

  "from command line" should "do the same thing" in {
    import spray.json._
    val x =
    try {
      val p = Runtime.getRuntime().exec("cat /log")
      val stdInput = new BufferedReader(new InputStreamReader(p.getInputStream))
      val stdError = new BufferedReader(new InputStreamReader(p.getErrorStream))
      var s = ""
      for (line <- stdInput.lines().iterator())
        s += line + "\n"
      if (stdError.lines().iterator().hasNext)
        "stdError"
      else {
        val x = MonitOutputParser.parseOption(s)
        x match{
          case Some(_) => {
            import DefaultJsonProtocol._
            x.get.toJson.toString
          }
          case None => "parse error"
        }
      }
    }
    catch{
      case e : IOException => "IOerror"
    }
    println(x)
  }
}
