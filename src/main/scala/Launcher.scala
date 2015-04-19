
import it.unibo.ing.smacs.monit.gatherer.{DataGatherer, RecentBuffer}
import it.unibo.ing.smacs.monit.model.MonitInfo
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{DefaultServlet, ServletContextHandler}
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener
import org.slf4j.LoggerFactory

object JettyLauncher {
  // this is my entry object as specified in sbt project definition
  val logger =  LoggerFactory.getLogger(getClass)
  def main(args: Array[String]) {
    startDataGatherer()
    val defaultPort = 9876
    val port = if (args.length == 1) {
      try {
        args(0).toInt
      }
      catch {
        case _: Throwable => defaultPort
      }
    }
    else if (System.getenv("PORT") != null) System.getenv("PORT").toInt else defaultPort
    val server = new Server(port)
    val context = new WebAppContext()
    context setContextPath "/"
    context.setResourceBase("src/main/webapp")
    context.addEventListener(new ScalatraListener)
    context.addServlet(classOf[DefaultServlet], "/")

    server.setHandler(context)

    server.start
    server.join
  }
  def startDataGatherer() = {
    import java.util.concurrent._
    import java.util.Date
    import java.util.{Timer, TimerTask}
    logger.debug("startDataGatherer")
    val task = new TimerTask() {
      override def run() : Unit = {
        logger.debug("gathering data")
        DataGatherer.getData() match{
          case Some(s : Seq[MonitInfo]) => RecentBuffer(new Date())
          case None =>
        }
      }
    }
    val timer = new Timer()
    val delay = 0
    val intevalPeriod = 1000 * 60
    timer.scheduleAtFixedRate(task, delay, intevalPeriod)
  }
}