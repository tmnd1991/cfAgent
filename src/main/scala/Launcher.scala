
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{DefaultServlet, ServletContextHandler}
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener

object JettyLauncher {
  // this is my entry object as specified in sbt project definition
  def main(args: Array[String]) {
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
}