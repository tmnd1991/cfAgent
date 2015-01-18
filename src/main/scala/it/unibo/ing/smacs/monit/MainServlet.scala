package it.unibo.ing.smacs.monit

/**
 * @author Antonio Murgia
 *         Simple Servlet that launches the monit command and
 *         wraps it in a json object
 */

import java.io.{IOException, InputStreamReader, BufferedReader}
import it.unibo.ing.smacs.monit.model.MonitInfo
import it.unibo.ing.utils.BufferedLineIterator

import scala.collection.JavaConversions._
import scala.collection.immutable.Stream.Empty

import org.scalatra._

import spray.json._

import it.unibo.ing.smacs.monit.parsers.MonitOutputParser
import it.unibo.ing.smacs.monit.model.JsonConversions._

class MainServlet extends MonitrestfulinterfaceStack {

  before() {
    contentType = "json"
  }

  get("/") {
    try {
      val p = Runtime.getRuntime().exec("/var/vcap/bosh/bin/monit status")
      val stdInput = new BufferedLineIterator(p.getInputStream)
      val stdError = new BufferedLineIterator(p.getErrorStream)
      val s = stdInput.mkString("\n")
      if (!stdError.isEmpty)
        JsObject("error" -> JsString("System Error")).toString
      else {
        import DefaultJsonProtocol._
        MonitOutputParser.parseOption(s) match {
          case Some(x: Seq[MonitInfo]) => {
            x.toJson
          }
          case None => JsObject("error" -> JsString("Parse Error")).toString
        }
      }
    }
    catch {
      case e: IOException => JsObject("error" -> JsString("I/O Error")).toString
      case t: Throwable => JsObject("exception" -> JsString(t.getMessage)).toString
    }
  }
}